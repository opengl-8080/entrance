package entrance.view.javafx.window

import entrance.application.deletion.DeleteImageService
import entrance.domain.viewer.StoredImage
import entrance.domain.viewer.StoredImageRepository
import entrance.view.javafx.component.RankSelectController
import entrance.view.javafx.component.TagSelectController
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.window.categorization.CategorizationWindow
import entrance.view.javafx.window.entry.EntryProgressWindow
import entrance.view.javafx.window.tag.TagMaintenanceWindow
import entrance.view.javafx.window.tag.category.TagCategoryMaintenanceWindow
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
    private val deleteImageService: DeleteImageService,
    private val tagCategoryMaintenanceWindow: TagCategoryMaintenanceWindow,
    private val entryProgressWindow: EntryProgressWindow
) : Initializable {
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var rankSelectController: RankSelectController
    
    lateinit var primaryStage: Stage
    
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var openImageMenuItem: MenuItem

    lateinit var thumbnailsView: ThumbnailsView<StoredImage>
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane)
    }
    
    @FXML
    fun openTagCategoryMaintenance() {
        tagCategoryMaintenanceWindow.open(primaryStage)
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
            storedImageRepository.findNotTaggedImage(rankSelectController.condition)
        } else {
            storedImageRepository.find(tagSelectController.selectedTagSet, rankSelectController.condition)
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
    
    @FXML
    fun executeEntry() {
        if (Dialog.confirm("画像の読み込みを開始します。\nよろしいですか？")) {
            entryProgressWindow.open(primaryStage)
        }
    }
}
