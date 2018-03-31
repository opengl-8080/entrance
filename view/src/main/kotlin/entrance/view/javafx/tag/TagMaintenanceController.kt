package entrance.view.javafx.tag

import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagListCellFactory
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.stage.Stage
import javafx.util.Callback
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
    lateinit var tagListView: ListView<Tag>
    
    private val tagListItems = FXCollections.observableArrayList<Tag>()
    lateinit var filteredTagListItems: FilteredList<Tag>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        filteredTagListItems = tagListItems.filtered {true}
        tagListView.items = filteredTagListItems
        tagListView.cellFactory = TagListCellFactory()
        
        loadTags()
        filterTextFiled.textProperty().addListener {_, _, inputText ->
            filteredTagListItems.predicate = Predicate { it.matches(inputText) }
        }
    }

    @FXML
    fun add() {
        registerTagWindow.open(stage)
        loadTags()
    }
    
    @FXML
    fun modify() {
        val selectedTag = tagListView.selectionModel.selectedItem
        if (selectedTag != null) {
            modifyTagWindow.open(stage, selectedTag.name)
            loadTags()
        }
    }
    
    @FXML
    fun remove() {
        
    }
    
    private fun loadTags() {
        tagListItems.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            tagListItems.add(tag)
        }
    }
}
