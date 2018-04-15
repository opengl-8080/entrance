package entrance.view.javafx.control

import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane

class TagSelectionView(
    private val tagRepository: TagRepository,
    private val pane: FlowPane,
    private val filterTextField: TextField,
    selectedTagListView: ListView<Tag>
) {
    private val tagViewList: ObservableList<TagView> = FXCollections.observableArrayList()
    val selectedTagList: ObservableList<Tag> = FXCollections.observableArrayList<Tag>()
    
    init {
        reload()

        filterTextField.textProperty().addListener { _, _, text ->
            pane.children
                    .map { child -> child as TagView }
                    .forEach { tagView -> tagView.controlVisibility(text) }
        }

        selectedTagListView.cellFactory = TagListCellFactory()
        selectedTagListView.items = selectedTagList
    }
    
    fun isNotSelected(): Boolean = selectedTagList.isEmpty()
    
    fun selectAll(tagSet: Set<Tag>) {
        tagViewList.filter { tagSet.contains(it.tag) }.forEach {
            it.isSelected = true
        }
    }
    
    fun clearSelect() {
        tagViewList.filter { it.isSelected }.forEach { it.isSelected = false }
        filterTextField.text = ""
    }
    
    fun reload() {
        pane.children.clear()
        tagViewList.clear()
        selectedTagList.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            val tagView = TagView(tag)
            tagViewList += tagView
            pane.children += tagView

            tagView.selectedProperty().addListener { _, _, selected ->
                if (selected) {
                    selectedTagList += tag
                } else {
                    selectedTagList -= tag
                }
            }
        }
    }
}