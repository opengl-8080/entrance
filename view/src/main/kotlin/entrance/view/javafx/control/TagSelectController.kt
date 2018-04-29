package entrance.view.javafx.control

import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.tag.TagsPaneController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Accordion
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import java.net.URL
import java.util.*

@FXPrototypeController
class TagSelectController(
    private val tagRepository: TagRepository,
    private val tagCategoryRepository: TagCategoryRepository
): Initializable {

    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagAccordion: Accordion
    @FXML
    lateinit var selectedTagsListView: ListView<Tag>

    var onReloaded: () -> Unit = {}
    
    private val tagsPaneList = mutableListOf<TagsPaneController>()
    
    val selectedTagViewList: List<TagView>
        get() = tagsPaneList.flatMap { it.selectedTagViewList }
    val selectedTagList: ObservableList<Tag> = FXCollections.observableArrayList<Tag>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        reloadTags()

        tagFilterTextField.textProperty().addListener { _, _, text ->
            val expandedPane = tagAccordion.panes.firstOrNull { it.isExpanded }
            
            tagAccordion.panes.clear()
            
            tagsPaneList.forEach { tagsPane ->
                tagsPane.filter(text)

                if (text.isEmpty() || tagsPane.hasVisibleTag()) {
                    tagAccordion.panes += tagsPane
                    
                    if (expandedPane == tagsPane) {
                        tagsPane.isExpanded = true
                    }
                }
            }
        }

        selectedTagsListView.cellFactory = TagListCellFactory()
        selectedTagsListView.items = selectedTagList
    }

    fun isNotSelected(): Boolean = selectedTagList.isEmpty()
    
    @FXML
    fun reloadTags() {
        tagFilterTextField.text = ""
        tagAccordion.panes.clear()
        tagsPaneList.clear()
        selectedTagList.clear()

        tagCategoryRepository.findAll().tagCategories.forEach { tagCategory ->
            val tags = tagRepository.findByTagCategory(tagCategory)

            val tagsPane = TagsPaneController()
            tagsPane.init(tagCategory, tags, onSelected = { tag, selected ->
                if (selected) {
                    selectedTagList += tag
                } else {
                    selectedTagList -= tag
                }
            })
            
            tagsPaneList += tagsPane
            tagAccordion.panes += tagsPane
        }
        
        onReloaded()
    }
    
    @FXML
    fun clearTagSelect() {
        tagsPaneList.forEach { it.clearTagSelect() }
        tagFilterTextField.text = ""
    }
    
    @FXML
    fun deselectTag() {
        val tag = selectedTagsListView.selectionModel.selectedItem
        tagsPaneList.forEach { it.deselectTag(tag) }
    }
}