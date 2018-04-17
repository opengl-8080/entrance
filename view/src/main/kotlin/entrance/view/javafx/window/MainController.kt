package entrance.view.javafx.window

import entrance.application.deletion.DeleteImageService
import entrance.domain.viewer.StoredImage
import entrance.domain.viewer.StoredImageRepository
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.window.categorization.CategorizationWindow
import entrance.view.javafx.window.tag.TagMaintenanceWindow
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.MenuItem
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
        private val tagMaintenanceWindow: TagMaintenanceWindow,
        private val categorizationWindow: CategorizationWindow,
        private val storedImageRepository: StoredImageRepository,
        private val singleImageViewerWindow: SingleImageViewerWindow,
        private val deleteImageService: DeleteImageService
) : Initializable {
    @FXML
    lateinit var tagSelectController: TagSelectController
    
    lateinit internal var primaryStage: Stage
    
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var openImageMenuItem: MenuItem

    lateinit var thumbnailsView: ThumbnailsView<StoredImage>
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane)
    }
    
    @FXML
    fun openTagMaintenance() {
        tagMaintenanceWindow.open(primaryStage)
    }
    
    @FXML
    fun openCategorizationWindow() {
        categorizationWindow.open(primaryStage)
    }
    
    @FXML
    fun search() {
        thumbnailsView.images = if (tagSelectController.isNotSelected()) {
            storedImageRepository.findNotTaggedImage()
        } else {
            storedImageRepository.find(tagSelectController.selectedTagList)
        }
    }
    
    @FXML
    fun openImage() {
        thumbnailsView.selectedThumbnail?.apply {
            singleImageViewerWindow.open(imageFile, thumbnailsView.images)
        }
    }
    
    @FXML
    fun deleteImage() {
        thumbnailsView.selectedThumbnail?.apply {
            if (Dialog.confirm("削除してもよろしいですか？")) {
                deleteImageService.delete(imageFile)
                search()
            }
        }
    }
}
