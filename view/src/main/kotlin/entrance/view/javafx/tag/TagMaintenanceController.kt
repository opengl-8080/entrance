package entrance.view.javafx.tag

import entrance.domain.tag.TagRepository
import entrance.view.javafx.EntranceFXMLLoader
import entrance.view.javafx.InjectOwnStage
import entrance.view.javafx.StageTitle
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*
import java.util.function.Predicate

@Component
class TagMaintenanceController (
    private val fxmlLoader: EntranceFXMLLoader,
    private val tagRepository: TagRepository
): InjectOwnStage, StageTitle, Initializable {
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        filteredTagListItems = tagListItems.filtered {true}
        tagListView.items = filteredTagListItems
        loadTags()
        filterTextFiled.textProperty().addListener {_, _, value ->
            filteredTagListItems.predicate = Predicate { it.toUpperCase().contains(value.toUpperCase()) }
        }
    }

    override lateinit var ownStage: Stage
    override val title = "タグメンテナンス"
    
    @FXML
    lateinit var filterTextFiled: TextField
    @FXML
    lateinit var tagListView: ListView<String>
    
    private val tagListItems = FXCollections.observableArrayList<String>()
    lateinit var filteredTagListItems: FilteredList<String>
    
    @FXML
    fun add() {
        fxmlLoader.showAsModal<RegisterTagController>("tag/register-tag.fxml", owner = ownStage)
        loadTags()
    }
    
    private fun loadTags() {
        tagListItems.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            tagListItems.add(tag.name.value)
        }
    }
}
