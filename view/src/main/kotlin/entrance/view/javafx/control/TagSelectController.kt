package entrance.view.javafx.control

import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.util.FXPrototypeController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import java.net.URL
import java.util.*

@FXPrototypeController
class TagSelectController(
    private val tagRepository: TagRepository
): Initializable {

    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var selectedTagsListView: ListView<Tag>

    var onReloaded: () -> Unit = {}
    
    private val tagViewList: ObservableList<TagView> = FXCollections.observableArrayList()
    val selectedTagViewList: List<TagView>
        get() = tagViewList.filter { it.isSelected }
    val selectedTagList: ObservableList<Tag> = FXCollections.observableArrayList<Tag>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        reloadTags()

        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagsFlowPane.children
                    .map { child -> child as TagView }
                    .forEach { tagView -> tagView.controlVisibility(text) }
        }

        selectedTagsListView.cellFactory = TagListCellFactory()
        selectedTagsListView.items = selectedTagList
    }

    fun isNotSelected(): Boolean = selectedTagList.isEmpty()

    fun selectAll(tagSet: Set<Tag>) {
        tagViewList.filter { tagSet.contains(it.tag) }.forEach {
            it.isSelected = true
        }
    }
    
    @FXML
    fun reloadTags() {
        tagFilterTextField.text = ""
        tagsFlowPane.children.clear()
        tagViewList.clear()
        selectedTagList.clear()

        tagRepository.findAll().tags.forEach { tag ->
            val tagView = TagView(tag)
            tagViewList += tagView
            tagsFlowPane.children += tagView

            tagView.selectedProperty().addListener { _, _, selected ->
                if (selected) {
                    selectedTagList += tag
                } else {
                    selectedTagList -= tag
                }
            }
        }

        onReloaded()
    }
    
    @FXML
    fun clearTagSelect() {
        tagViewList.filter { it.isSelected }.forEach { it.isSelected = false }
        tagFilterTextField.text = ""
    }
    
    @FXML
    fun deselectTag() {
        val tag = selectedTagsListView.selectionModel.selectedItem
        tagViewList.filter { it.tag == tag }.forEach { it.isSelected = false }
    }
}