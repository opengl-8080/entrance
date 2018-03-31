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
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var assignedImages: VBox
    @FXML
    lateinit var assignButton: Button
    
    private val thumbnailViewList = mutableListOf<ThumbnailView>()
    
    val tagToggleGroup = ToggleGroup()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val notCategorizedImageList = notCategorizedImageRepository.loadAll()

        addNotCategorizedImageList(notCategorizedImageList)
        
        val tagRadioButtons = tagRepository.findAll().tags.map { tag ->
            RadioButton(tag.name.value).apply { 
                this.managedProperty().bind(this.visibleProperty())
                this.toggleGroup = tagToggleGroup
                this.userData = tag
            }
        }

        tagRadioButtons.forEach { tagsFlowPane.children.add(it) }
        
        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagRadioButtons
                .forEach { radio ->
                    val tag = radio.userData as Tag
                    radio.isVisible = tag.matches(text)
                }
        }
    }
    
    private val assignedTagControllerMap = mutableMapOf<Tag, AssignedImageController>()
    
    @FXML
    fun assign() {
        val selectedRadioButton = tagToggleGroup.selectedToggle as RadioButton?
        if (selectedRadioButton == null) {
            Dialog.warn("タグが選択されていません")
            return
        }
        
        val selectedTag = selectedRadioButton.userData as Tag

        val selectedThumbnailList = thumbnailViewList.filter { it.selected }
        if (selectedThumbnailList.isEmpty()) {
            Dialog.warn("画像が選択されていません")
            return
        }

        removeNotCategorizedThumbnailViewList(selectedThumbnailList)
        
        val selectedNotCategorizedImageList = selectedThumbnailList.map { it.notCategorizedImage }
        
        if (selectedTag in assignedTagControllerMap) {
            assignedTagControllerMap[selectedTag]!!.addImage(selectedNotCategorizedImageList)
        } else {
            val context = fxmlLoader.load<AssignedImageController>("categorization/assigned-image.fxml")
            context.controller.init(selectedTag, selectedNotCategorizedImageList)
            assignedTagControllerMap[selectedTag] = context.controller
            assignedImages.children += context.root
        }
        
        tagToggleGroup.selectToggle(null)
    }
    
    @FXML
    fun unassign() {
        val selectedThumbnailViewList = assignedTagControllerMap.values.flatMap { it.takeSelectedImageList() }

        addNotCategorizedImageList(selectedThumbnailViewList)

        val empties = assignedTagControllerMap.entries.filter { it.value.isEmpty() }
        
        empties.forEach { entry ->
            assignedImages.children -= entry.value.root
            assignedTagControllerMap.remove(entry.key)
        }
    }
    
    private fun addNotCategorizedImageList(notCategorizedImageList: List<NotCategorizedImage>) {
        notCategorizedImageList
                .map { ThumbnailView(it) }
                .forEach {
                    notCategorizedImagesPane.children += it
                    thumbnailViewList += it
                }
    }
    
    private fun removeNotCategorizedThumbnailViewList(notCategorizedImageList: List<ThumbnailView>) {
        notCategorizedImageList.forEach {
            notCategorizedImagesPane.children -= it
            thumbnailViewList -= it
        }
    }
}
