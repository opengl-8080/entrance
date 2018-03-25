package entrance.view.javafx.tag

import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.ContextMenuEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*
import java.util.function.Predicate

@Component
class TagMaintenanceController (
    private val registerTagWindow: RegisterTagWindow,
    private val modifyTagWindow: ModifyTagWindow,
    private val tagRepository: TagRepository
): Initializable {
    
    lateinit internal var stage: Stage
    
    @FXML
    lateinit var filterTextFiled: TextField
    @FXML
    lateinit var tagListView: ListView<String>
    
    private val tagListItems = FXCollections.observableArrayList<String>()
    lateinit var filteredTagListItems: FilteredList<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        filteredTagListItems = tagListItems.filtered {true}
        tagListView.items = filteredTagListItems
        loadTags()
        filterTextFiled.textProperty().addListener {_, _, value ->
            filteredTagListItems.predicate = Predicate { it.toUpperCase().contains(value.toUpperCase()) }
        }
    }

    @FXML
    fun add() {
        registerTagWindow.open(stage)
        loadTags()
    }
    
    @FXML
    fun modify() {
        val selectedTagName = tagListView.selectionModel.selectedItem
        if (selectedTagName != null) {
            modifyTagWindow.open(stage, TagName(selectedTagName))
            loadTags()
        }
    }
    
    @FXML
    fun remove() {
        
    }
    
    private fun loadTags() {
        tagListItems.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            tagListItems.add(tag.name.value)
        }
    }
}
