package entrance.view.javafx.window.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.control.TagView
import entrance.view.javafx.control.ThumbnailsView
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
@Scope("prototype")
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

    lateinit var thumbnailsView: ThumbnailsView<TaggedImage>
    
    private val selectedTagList = FXCollections.observableArrayList<Tag>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane, multiSelect = true)
        
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
    fun search() {
        assignedTagFlowPane.children.clear()
        
        val imageList = if (selectedTagList.isEmpty()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(selectedTagList)
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
