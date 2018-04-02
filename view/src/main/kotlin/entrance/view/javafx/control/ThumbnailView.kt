package entrance.view.javafx.control

import entrance.domain.ImageFile
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton

class ThumbnailView<out T: ImageFile>(
    val imageFile: T
): ImageView(Image(imageFile.stringPath)) {
    private val selectedCssClass = "thumbnail--selected"
    var selected = false
        private set
    
    init {
        fitWidth = 100.0
        fitHeight = 100.0
        isPreserveRatio = true
        isSmooth = true
        
        onMouseClicked = EventHandler { e ->
            if (e.button == MouseButton.PRIMARY) {
                switchSelect()
            }
        }
    }
    
    fun switchSelect() {
        if (selected) {
            select()
        } else {
            deselect()
        }
    }
    
    fun select() {
        if (selected) {
            return
        }

        styleClass.add(selectedCssClass)
        selected = true
    }
    
    fun deselect() {
        if (!selected) {
            return
        }

        styleClass.remove(selectedCssClass)
        selected = false
    }
}