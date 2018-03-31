package entrance.view.javafx.control

import entrance.domain.categorization.NotCategorizedImage
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class ThumbnailView(
    val notCategorizedImage: NotCategorizedImage
): ImageView(Image(notCategorizedImage.uriString)) {
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