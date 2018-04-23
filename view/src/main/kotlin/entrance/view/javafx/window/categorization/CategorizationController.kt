package entrance.view.javafx.window.categorization

import entrance.application.categorization.CategorizeImageService
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.tag.Tag
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.control.TagView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.net.URL
import java.util.*

@FXPrototypeController
class CategorizationController (
    private val taggedImageRepository: TaggedImageRepository,
    private val singleImageViewerWindow: SingleImageViewerWindow,
    private val categorizeImageService: CategorizeImageService
): Initializable {
    private lateinit var ownStage: Stage
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var tagSelect2Controller: TagSelectController
    @FXML
    lateinit var assignedTagHistoryPane: FlowPane
    @FXML
    lateinit var imagesVBox: VBox
        
    lateinit var taggedImageCardListView: TaggedImageCardListView

    private val tagHistoryViewSet = mutableSetOf<TagView>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        taggedImageCardListView = TaggedImageCardListView(imagesVBox)

        tagSelectController.onReloaded = {
            taggedImageCardListView.clear()
        }
    }
    
    fun init(ownStage: Stage) {
        this.ownStage = ownStage
    }

    @FXML
    fun search() {
        val imageList = if (tagSelectController.isNotSelected()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(tagSelectController.selectedTagList)
        }

        taggedImageCardListView.replaceAll(imageList)
    }
    
    @FXML
    fun openViewer() {
        if (taggedImageCardListView.selected) {
            val selectedImages = taggedImageCardListView.selectedImages
            val firstImage = selectedImages.first()
            singleImageViewerWindow.open(firstImage, selectedImages)
        }
    }
    
    @FXML
    fun clearImageSelect() {
        taggedImageCardListView.clearSelect()
    }
    
    @FXML
    fun save() {
        if (!Dialog.confirm("タグの編集を保存します\nよろしいですか？")) {
            return
        }
        
        val modifiedImages = taggedImageCardListView.filterModifiedImages()
        categorizeImageService.categorize(modifiedImages)
        search()
    }
    
    @FXML
    fun assignTag() {
        val selectedTagList = tagSelect2Controller.selectedTagList
        taggedImageCardListView.assign(selectedTagList.toSet())
        saveHistory(selectedTagList)
        tagSelect2Controller.clearTagSelect()
    }

    @FXML
    fun releaseTag() {
        val selectedTagList = tagSelect2Controller.selectedTagList
        taggedImageCardListView.release(selectedTagList.toSet())
        saveHistory(selectedTagList)
        tagSelect2Controller.clearTagSelect()
    }

    private fun saveHistory(selectedTagList: List<Tag>) {
        val newHistories = tagSelect2Controller
                .selectedTagViewList
                .filter { originalTagView ->
                    tagHistoryViewSet.all { !it.hasSameTag(originalTagView.tag) }
                }
                .map { originalTagView ->
                    TagView(originalTagView.tag).apply {
                        selectedProperty().bindBidirectional(originalTagView.selectedProperty())
                    }
                }

        tagHistoryViewSet.addAll(newHistories)

        assignedTagHistoryPane.children.clear()
        tagHistoryViewSet.forEach { assignedTagHistoryPane.children += it }
    }

    @FXML
    fun clearTagHistory() {
        assignedTagHistoryPane.children.clear()
        tagHistoryViewSet.clear()
    }
}
