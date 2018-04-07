package entrance.view.javafx.window

import entrance.domain.image.Image
import entrance.domain.image.ImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.control.TagSelectionView
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.window.categorization.CategorizationWindow
import entrance.view.javafx.window.tag.TagMaintenanceWindow
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val tagMaintenanceWindow: TagMaintenanceWindow,
    private val categorizationWindow: CategorizationWindow,
    private val tagRepository: TagRepository,
    private val imageRepository: ImageRepository,
    private val singleImageViewerWindow: SingleImageViewerWindow
) : Initializable {
    
    lateinit internal var primaryStage: Stage
    
    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var selectedTagsListView: ListView<Tag>
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var openImageMenuItem: MenuItem

    lateinit var thumbnailsView: ThumbnailsView<Image>
    lateinit var tagSelectionView: TagSelectionView
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane)
        tagSelectionView = TagSelectionView(tagRepository, tagsFlowPane, tagFilterTextField, selectedTagsListView)
        
        selectedTagsListView.cellFactory = TagListCellFactory()
        selectedTagsListView.items = tagSelectionView.selectedTagList
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
        thumbnailsView.images = imageRepository.find(tagSelectionView.selectedTagList)
    }
    
    @FXML
    fun openImage() {
        thumbnailsView.selectedThumbnail?.apply {
            singleImageViewerWindow.open(imageFile, thumbnailsView.images)
        }
    }
}
