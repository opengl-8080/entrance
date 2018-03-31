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
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
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
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        notCategorizedImageRepository
            .loadAll()
            .forEach {
                val thumbnailView = ThumbnailView(Image(it.uriString))
                notCategorizedImagesPane.children += thumbnailView
            }

        val radioTagMap = mutableMapOf<String, Tag>()

        val toggleGroup = ToggleGroup()
        
        val tagRadioButtons = tagRepository.findAll().tags.map { tag ->
            radioTagMap.put(tag.name.value, tag)
            RadioButton(tag.name.value).apply { 
                this.managedProperty().bind(this.visibleProperty())
                this.toggleGroup = toggleGroup
            }
        }

        tagRadioButtons.forEach { tagsFlowPane.children.add(it) }
        
        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagRadioButtons
                .forEach { radio ->
                    radio.isVisible = radioTagMap[radio.text]?.matches(text) ?: false
                }
        }
    }
}
