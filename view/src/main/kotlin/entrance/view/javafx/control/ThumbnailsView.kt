package entrance.view.javafx.control

import entrance.domain.ImageFile
import javafx.event.EventHandler
import javafx.scene.input.MouseButton
import javafx.scene.layout.FlowPane

class ThumbnailsView<T: ImageFile>(
    private val flowPane: FlowPane,
    private val multiSelect: Boolean = false
) {

    private val thumbnails = mutableListOf<ThumbnailView<T>>()
    private val _images = mutableListOf<T>()

    var selectedThumbnail: ThumbnailView<T>? = null
        private set
        get() = if (field != null) {
            field
        } else {
            thumbnails.firstOrNull { it.selected }
        }
    
    val selectedImages: Set<T>
        get() = thumbnails.filter { it.selected }.map { it.imageFile }.toSet()
    
    var onSelected: () -> Unit = {}
    
    var images: List<T>
        get() = _images.toList()
        
        set(value) {
            selectedThumbnail = null
            _images.clear()
            _images.addAll(value)

            thumbnails.clear()
            flowPane.children.clear()
            _images
                    .map { ThumbnailView(it) }
                    .forEach { thumbnail ->
                        thumbnail.selectedProperty.addListener { _, _, _ ->
                            onSelected()
                        }
                        
                        if (!multiSelect) {
                            thumbnail.onMouseClicked = EventHandler { e ->
                                if (e.button != MouseButton.PRIMARY) {
                                    return@EventHandler
                                }

                                if (selectedThumbnail == thumbnail) {
                                    thumbnail.deselect()
                                    selectedThumbnail = null
                                } else {
                                    selectedThumbnail?.deselect()
                                    thumbnail.select()
                                    selectedThumbnail = thumbnail
                                }
                            }
                        }

                        thumbnails += thumbnail
                        flowPane.children += thumbnail
                    }
        }
    
    fun clear() {
        images = emptyList()
    }

    fun clearSelect() {
        thumbnails.filter { it.selected }.forEach { it.deselect() }
    }
}
