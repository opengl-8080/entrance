package entrance.view.javafx.window.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagSelectionView
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

@FXPrototypeController
class CategorizationController (
    private val tagRepository: TagRepository,
    private val taggedImageRepository: TaggedImageRepository,
    private val categorizeTagWindow: CategorizeTagWindow
): Initializable {
    lateinit var ownStage: Stage

    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var selectedTagsListView: ListView<Tag>
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var assignedTagFlowPane: FlowPane

    lateinit var tagSelectionView: TagSelectionView
    lateinit var thumbnailsView: ThumbnailsView<TaggedImage>
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane, multiSelect = true)
        tagSelectionView = TagSelectionView(tagRepository, tagsFlowPane, tagFilterTextField, selectedTagsListView)
    }

    @FXML
    fun search() {
        assignedTagFlowPane.children.clear()
        
        val imageList = if (tagSelectionView.isNotSelected()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(tagSelectionView.selectedTagList)
        }
        
        thumbnailsView.images = imageList
        
        thumbnailsView.onSelected = {
            val selectedImageList = thumbnailsView.selectedImages

            val imageUnit = CategorizationImageUnit(selectedImageList)
            val commonAssignedTags = imageUnit.commonAssignedTags

            assignedTagFlowPane.children.clear()

            commonAssignedTags.names.forEach { tagName ->
                assignedTagFlowPane.children += Label(tagName)
            }
        }
    }

    @FXML
    fun openManagementImageTagWindow() {
        val selectedImages = thumbnailsView.selectedImages
        categorizeTagWindow.open(ownStage, CategorizationImageUnit(selectedImages), {
            search()
        })
    }
}
