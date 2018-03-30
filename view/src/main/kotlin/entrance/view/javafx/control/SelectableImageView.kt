package entrance.view.javafx.control

import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class SelectableImageView(image: Image): ImageView(image) {
    private val selectedCssClass = "thumbnail-list__thumbnail-selected"
    private var selected = false
    init {
        onMouseClicked = EventHandler { 
            if (selected) {
                styleClass.remove(selectedCssClass)
            } else {
                styleClass.add(selectedCssClass)
            }
            
            selected = !selected
        }
    }
}