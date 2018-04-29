package entrance.view.javafx.window.tag

import entrance.domain.tag.Tag
import entrance.domain.tag.category.TagCategory
import entrance.view.javafx.control.TagView
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.TitledPane
import javafx.scene.layout.FlowPane

class TagsPaneController: TitledPane() {
    @FXML
    lateinit var tagsFlowPane: FlowPane
    
    private val tagViewList = mutableListOf<TagView>()
    
    init {
        val loader = FXMLLoader(javaClass.getResource("/fxml/tag/tags-pane.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        
        loader.load<Any>()
    }
    
    val selectedTagViewList: List<TagView>
        get() = tagViewList.filter { it.isSelected }
    
    fun init(tagCategory: TagCategory, tags: List<Tag>, onSelected: (Tag, Boolean) -> Unit) {
        text = tagCategory.name.value

        tags.forEach { tag ->
            val tagView = TagView(tag)
            tagViewList += tagView
            tagsFlowPane.children += tagView
            
            tagView.selectedProperty().addListener { _, _, selected ->
                onSelected(tag, selected)
            }
        }
    }
    
    fun filter(text: String) {
        tagViewList.forEach { it.controlVisibility(text) }
    }
    
    fun hasVisibleTag(): Boolean {
        return tagViewList.any { it.isVisible }
    }
    
    fun clearTagSelect() {
        tagViewList.filter { it.isSelected }.forEach { it.isSelected = false }
    }
    
    fun deselectTag(tag: Tag) {
        tagViewList.filter { it.tag == tag }.forEach { it.isSelected = false }
    }
}