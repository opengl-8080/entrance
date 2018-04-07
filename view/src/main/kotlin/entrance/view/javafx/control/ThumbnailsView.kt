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
    private val imagesProperty = mutableListOf<T>()

    var selectedThumbnail: ThumbnailView<T>? = null
        private set
    
    val selectedImages: Set<T>
        get() = thumbnails.filter { it.selected }.map { it.imageFile }.toSet()
    
    var onSelected: () -> Unit = {}
    
    var images: List<T>
        get() = imagesProperty
        
        set(value) {
            imagesProperty.clear()
            imagesProperty.addAll(value)

            thumbnails.clear()
            flowPane.children.clear()
            imagesProperty
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
}
