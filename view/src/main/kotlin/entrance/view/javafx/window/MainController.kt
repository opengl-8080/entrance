package entrance.view.javafx.window

import entrance.domain.image.Image
import entrance.domain.image.ImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.control.TagView
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.window.categorization.CategorizationWindow
import entrance.view.javafx.window.tag.TagMaintenanceWindow
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.collections.FXCollections
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
    
    private val selectedTagList = FXCollections.observableArrayList<Tag>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane)
        
        selectedTagsListView.cellFactory = TagListCellFactory()
        
        selectedTagsListView.items = selectedTagList
        
        tagRepository.findAll().tags.forEach { tag ->
            val tagView = TagView(tag)
            tagsFlowPane.children += tagView

            tagView.selectedProperty().addListener { _, _, selected ->
                if (selected) {
                    selectedTagList += tag
                } else {
                    selectedTagList -= tag
                }
            }
        }

        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagsFlowPane.children.map { child -> child as TagView }.forEach { tagView -> tagView.controlVisibility(text) }
        }
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
        thumbnailsView.images = imageRepository.find(selectedTagList)
    }
    
    @FXML
    fun openImage() {
        thumbnailsView.selectedThumbnail?.apply {
            singleImageViewerWindow.open(imageFile, thumbnailsView.images)
        }
    }
}
