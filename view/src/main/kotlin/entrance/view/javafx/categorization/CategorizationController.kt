package entrance.view.javafx.categorization

import entrance.domain.categorization.NotTaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.control.TagView
import entrance.view.javafx.control.ThumbnailView
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
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
    lateinit var thumbnailsFlowPane: FlowPane

    private val thumbnailViewList = mutableListOf<ThumbnailView<NotTaggedImage>>()
    private val selectedTagList = FXCollections.observableArrayList<Tag>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
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
        thumbnailViewList.clear()
        
        val imageList = if (selectedTagList.isEmpty()) {
            taggedImageRepository.findNotTaggedImages()
        } else {
            taggedImageRepository.findTaggedImages(selectedTagList)
        }
        
        thumbnailsFlowPane.children.clear()
        imageList.map { ThumbnailView(it) }
                .forEach { thumbnailView ->
                    thumbnailViewList += thumbnailView
                    thumbnailsFlowPane.children += thumbnailView
                }
    }

    @FXML
    fun openManagementImageTagWindow() {
        val selectedImageList = thumbnailViewList.filter { it.selected }.map { it.imageFile }
        categorizeTagWindow.open(ownStage, selectedImageList)
    }
}
