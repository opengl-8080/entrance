package entrance.view.javafx.control

import entrance.domain.ImageFile
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.value.ObservableBooleanValue
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton

class ThumbnailView<out T: ImageFile>(
    val imageFile: T
): ImageView(Image(imageFile.thumbnailUri.toString())) {
    private val selectedCssClass = "thumbnail--selected"
    
    private val _selectedProperty = ReadOnlyBooleanWrapper(false)
    val selectedProperty: ReadOnlyBooleanProperty
        get() = _selectedProperty.readOnlyProperty
    val selected: Boolean
        get() = selectedProperty.get()
    
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
    
    private fun switchSelect() {
        if (selected) {
            deselect()
        } else {
            select()
        }
    }
    
    fun select() {
        if (selected) {
            return
        }

        styleClass.add(selectedCssClass)
        _selectedProperty.value = true
    }
    
    fun deselect() {
        if (!selected) {
            return
        }

        styleClass.remove(selectedCssClass)
        _selectedProperty.value = false
    }
}