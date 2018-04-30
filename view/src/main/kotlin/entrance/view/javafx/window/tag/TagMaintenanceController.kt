package entrance.view.javafx.window.tag

import entrance.application.tag.DeleteTagService
import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.domain.viewer.StoredImageRepository
import entrance.view.javafx.control.TagListCellFactory
import entrance.view.javafx.util.Dialog
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
    private val registerTagWindow: RegisterTagWindow,
    private val modifyTagWindow: ModifyTagWindow,
    private val tagRepository: TagRepository,
    private val imageRepository: StoredImageRepository,
    private val deleteTagService: DeleteTagService
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
        val selectedTag = tagListView.selectionModel.selectedItem
        if (selectedTag != null) {
            val tags = listOf<Tag>(selectedTag)
            val rankCondition = RankCondition(Rank.FIVE, Rank.ONE)
            val images = imageRepository.find(tags, rankCondition)
            
            val message = if (images.isEmpty()) {
                "タグを削除します。よろしいですか？"
            } else {
                """このタグを使用している画像が存在します。
                |タグを削除すると、画像からもタグの割り当てが解除されます。
                |削除してもよろしいですか？""".trimMargin()
            }
            
            if (Dialog.confirm(message)) {
                deleteTagService.delete(selectedTag)
                loadTags()
            }
        }
    }
    
    private fun loadTags() {
        tagListItems.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            tagListItems.add(tag)
        }
    }
}
