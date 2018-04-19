package entrance.view.javafx.window.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

@FXPrototypeController
class CategorizationController (
    private val taggedImageRepository: TaggedImageRepository,
    private val categorizeTagWindow: CategorizeTagWindow,
    private val singleImageViewerWindow: SingleImageViewerWindow
): Initializable {
    lateinit var ownStage: Stage
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var assignedTagFlowPane: FlowPane

    lateinit var thumbnailsView: ThumbnailsView<TaggedImage>
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane, multiSelect = true)

        tagSelectController.onReloaded = {
            thumbnailsView.clear()
            assignedTagFlowPane.children.clear()
        }
    }

    @FXML
    fun search() {
        assignedTagFlowPane.children.clear()
        
        val imageList = if (tagSelectController.isNotSelected()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(tagSelectController.selectedTagList)
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
    fun openCategorizeTagWindow() {
        val selectedImages = thumbnailsView.selectedImages
        
        if (!selectedImages.isEmpty()) {
            categorizeTagWindow.open(ownStage, CategorizationImageUnit(selectedImages), {
                search()
            })
        }
    }
    
    @FXML
    fun openViewer() {
        thumbnailsView.selectedThumbnail?.apply {
            singleImageViewerWindow.open(imageFile, thumbnailsView.selectedImages.toList())
        }
    }
    
    @FXML
    fun clearImageSelect() {
        thumbnailsView.clearSelect()
    }
}
