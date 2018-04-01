package entrance.view.javafx.control

import entrance.domain.ImageFile
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class ThumbnailView<out T: ImageFile>(
    val imageFile: T
): ImageView(Image(imageFile.stringPath)) {
    private val selectedCssClass = "thumbnail--selected"
    var selected = false
        private set
    
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