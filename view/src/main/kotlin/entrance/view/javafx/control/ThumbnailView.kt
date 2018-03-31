package entrance.view.javafx.control

import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class ThumbnailView(image: Image): ImageView(image) {
    private val selectedCssClass = "thumbnail--selected"
    private var selected = false
    
    init {
        fitWidth = 100.0
        isPreserveRatio = true
        isSmooth = true
        
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