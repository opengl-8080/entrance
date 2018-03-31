package entrance.view.javafx.categorization

import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.control.TagView
import entrance.view.javafx.control.ThumbnailView
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
@Scope("prototype")
class CategorizationController (
    private val notCategorizedImageRepository: NotCategorizedImageRepository,
    private val tagRepository: TagRepository
): Initializable {
    internal lateinit var stage: Stage
    
    @FXML
    lateinit var notCategorizedImagesPane: FlowPane
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var selectedTagListView: ListView<String>
    lateinit var tagViewList: List<TagView>
    private val selectedTagList = FXCollections.observableArrayList<String>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        notCategorizedImageRepository
            .loadAll()
            .forEach {
                val thumbnailView = ThumbnailView(Image(it.uriString))
                notCategorizedImagesPane.children += thumbnailView
            }
        
        tagViewList = tagRepository.findAll().tags.map { TagView(it) }
        tagViewList.forEach { tagView ->
            tagView.selectedProperty().addListener { _, _, selected ->
                if (selected) {
                    selectedTagList.add(tagView.tag.name.value)
                } else {
                    selectedTagList.remove(tagView.tag.name.value)
                }
            }
        }
        selectedTagListView.items = selectedTagList
        
        tagsFlowPane.children.addAll(tagViewList)

        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagsFlowPane.children.clear()
            
            tagViewList.forEach { tagView ->
                if (tagView.tag.matches(text)) {
                    tagsFlowPane.children += tagView
                }
            }
        }
    }
}
