package entrance.view.javafx.window.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.categorization.TaggedImageRepository
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
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
    lateinit var assignedTagFlowPane: FlowPane
    @FXML
    lateinit var imagesVBox: VBox
    
    lateinit var taggedImageCardListView: TaggedImageCardListView
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        taggedImageCardListView = TaggedImageCardListView(imagesVBox)

        tagSelectController.onReloaded = {
            taggedImageCardListView.clear()
            assignedTagFlowPane.children.clear()
        }
    }

    @FXML
    fun search() {
        taggedImageCardListView.clear()
        assignedTagFlowPane.children.clear()
        
        val imageList = if (tagSelectController.isNotSelected()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(tagSelectController.selectedTagList)
        }

        taggedImageCardListView.add(imageList)

        taggedImageCardListView.onSelected = {
            val selectedImages = taggedImageCardListView.selectedImages.toSet()

            val imageUnit = CategorizationImageUnit(selectedImages)
            val commonAssignedTags = imageUnit.commonAssignedTags

            assignedTagFlowPane.children.clear()
            commonAssignedTags.names.forEach { tagName ->
                assignedTagFlowPane.children += Label(tagName)
            }
        }
    }

    @FXML
    fun openCategorizeTagWindow() {
        if (taggedImageCardListView.selected) {
            categorizeTagWindow.open(ownStage, CategorizationImageUnit(taggedImageCardListView.selectedImages.toSet()), {
                search()
            })
        }
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
}
