package entrance.view.javafx.categorization

import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.ThumbnailView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
@Scope("prototype")
class CategorizationController (
    private val notCategorizedImageRepository: NotCategorizedImageRepository,
    private val tagRepository: TagRepository,
    private val fxmlLoader: EntranceFXMLLoader
): Initializable {
    internal lateinit var stage: Stage
    
    @FXML
    lateinit var notCategorizedImagesPane: FlowPane
    @FXML
    lateinit var mainTagFilterTextField: TextField
    @FXML
    lateinit var mainTagFlowPane: FlowPane
    @FXML
    lateinit var assignedImages: VBox
    @FXML
    lateinit var assignButton: Button
    
    lateinit var mainTagSelectionViewModel: MainTagSelectionViewModel
    lateinit var notCategorizedImagesViewModel: NotCategorizedImagesViewModel
    lateinit var assignedImagesViewModel: AssignedImagesViewModel
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        mainTagSelectionViewModel = MainTagSelectionViewModel(mainTagFlowPane, mainTagFilterTextField, tagRepository)
        
        notCategorizedImagesViewModel = NotCategorizedImagesViewModel(notCategorizedImagesPane)
        notCategorizedImagesViewModel.addAll(notCategorizedImageRepository.loadAll())

        assignedImagesViewModel = AssignedImagesViewModel(assignedImages, fxmlLoader)
    }
    
    @FXML
    fun assign() {
        if (mainTagSelectionViewModel.isNotSelected) {
            Dialog.warn("タグが選択されていません")
            return
        }
        
        if (notCategorizedImagesViewModel.isNotSelected) {
            Dialog.warn("画像が選択されていません")
            return
        }
        
        val selectedNotCategorizedImageList = notCategorizedImagesViewModel.takeSelectedImages()

        assignedImagesViewModel.addAll(mainTagSelectionViewModel.selectedTag, selectedNotCategorizedImageList)

        mainTagSelectionViewModel.deselect()
    }
    
    @FXML
    fun unassign() {
        val selectedThumbnailViewList = assignedImagesViewModel.takeSelectedImageList()
        notCategorizedImagesViewModel.addAll(selectedThumbnailViewList)
    }
}

class MainTagSelectionViewModel(
    private val pane: Pane,
    tagFilterTextField: TextField,
    tagRepository: TagRepository
) {
    private val tagToggleGroup = ToggleGroup()
    val selectedTag: Tag
        get() = (tagToggleGroup.selectedToggle as RadioButton).userData as Tag
    val isNotSelected: Boolean
        get() = tagToggleGroup.selectedToggle as RadioButton? == null
    
    init {
        val tagRadioButtons = tagRepository.findAll().tags.map { tag ->
            RadioButton(tag.name.value).apply {
                this.managedProperty().bind(this.visibleProperty())
                this.toggleGroup = tagToggleGroup
                this.userData = tag
            }
        }

        tagRadioButtons.forEach { pane.children.add(it) }
        
        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagRadioButtons
                    .forEach { radio ->
                        val tag = radio.userData as Tag
                        radio.isVisible = tag.matches(text)
                    }
        }
    }
    
    fun deselect() = tagToggleGroup.selectToggle(null)
}

class AssignedImagesViewModel(
    private val assignedImages: Pane,
    private val fxmlLoader: EntranceFXMLLoader
) {
    private val assignedTagControllerMap = mutableMapOf<Tag, AssignedImageController>()
    
    fun addAll(selectedTag: Tag, selectedNotCategorizedImageList: List<NotCategorizedImage>) {
        if (selectedTag in assignedTagControllerMap) {
            assignedTagControllerMap[selectedTag]!!.addImage(selectedNotCategorizedImageList)
        } else {
            val context = fxmlLoader.load<AssignedImageController>("categorization/assigned-image.fxml")
            context.controller.init(selectedTag, selectedNotCategorizedImageList)
            assignedTagControllerMap[selectedTag] = context.controller
            assignedImages.children += context.root
        }
    }
    
    fun takeSelectedImageList(): List<NotCategorizedImage> {
        val selectedThumbnailViewList = assignedTagControllerMap.values.flatMap { it.takeSelectedImageList() }

        val empties = assignedTagControllerMap.entries.filter { it.value.isEmpty }

        empties.forEach { entry ->
            assignedImages.children -= entry.value.root
            assignedTagControllerMap.remove(entry.key)
        }
        
        return selectedThumbnailViewList
    }
}

class NotCategorizedImagesViewModel (
    private val pane: Pane
) {
    private val thumbnails = mutableListOf<ThumbnailView>()
    
    val isNotSelected: Boolean
        get() = thumbnails.all { !it.selected }
    
    fun addAll(list: List<NotCategorizedImage>) {
        list.map { ThumbnailView(it) }.forEach {
            pane.children += it
            thumbnails += it
        }
    }
    
    fun takeSelectedImages(): List<NotCategorizedImage> {
        val selectedThumbnails = thumbnails.filter { it.selected }

        selectedThumbnails.forEach { thumbnail ->
            pane.children -= thumbnail
            thumbnails.remove(thumbnail)
        }
        
        return selectedThumbnails.map { it.notCategorizedImage }
    }
}