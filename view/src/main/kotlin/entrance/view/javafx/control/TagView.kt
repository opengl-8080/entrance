package entrance.view.javafx.control

import entrance.domain.tag.Tag
import javafx.scene.control.ToggleButton

class TagView(val tag: Tag): ToggleButton(tag.name.value) {
    init {
        managedProperty().bind(visibleProperty())
        
        styleClass.add("tag")
        
        selectedProperty().addListener {_,_, selected ->
            if (selected) {
                styleClass += "tag--selected"
            } else {
                styleClass -= "tag--selected"
            }
        }
    }
    
    fun hasSameTag(tag: Tag): Boolean = tag == this.tag
    
    fun controlVisibility(text: String) {
        isVisible = tag.matches(text)
    }
}
