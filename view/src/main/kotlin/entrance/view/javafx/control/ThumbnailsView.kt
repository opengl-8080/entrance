package entrance.view.javafx.control

import entrance.domain.ThumbnailImage
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.event.EventHandler
import javafx.scene.input.MouseButton
import javafx.scene.layout.FlowPane

/**
 * 複数のサムネイル画像をまとめたビュー.
 */
class ThumbnailsView<T: ThumbnailImage>(
    private val flowPane: FlowPane
) {
    /**
     * 現在選択されているサムネイル.
     * 
     * １つも選択されていない場合は null になります.
     */
    var selectedThumbnail: ThumbnailView<T>? = null
        private set
    
    private val _selectedThumbnailProperty = ReadOnlyObjectWrapper<T>(null)

    /**
     * 現在選択されているサムネイルの読み取り専用のプロパティ.
     */
    val selectedThumbnailProperty: ReadOnlyObjectProperty<T>
        get() = _selectedThumbnailProperty.readOnlyProperty

    private val _images = mutableListOf<T>()

    /**
     * サムネイルとして表示する画像オブジェクトのリスト.
     * 
     * このプロパティに画像リストをセットすることで、全てのサムネイルを完全に置き換えることができます.
     */
    var images: List<T>
        get() = _images.toList()
        
        set(value) {
            selectedThumbnail = null
            _images.clear()
            _images.addAll(value)
            flowPane.children.clear()

            _images
                .map { ThumbnailView(it) }
                .forEach { thumbnail ->
                    flowPane.children += thumbnail
                    
                    thumbnail.onMouseClicked = EventHandler { e ->
                        if (e.button != MouseButton.PRIMARY) {
                            return@EventHandler
                        }
    
                        if (selectedThumbnail == thumbnail) {
                            thumbnail.deselect()
                            selectedThumbnail = null
                            _selectedThumbnailProperty.set(null)
                        } else {
                            selectedThumbnail?.deselect()
                            thumbnail.select()
                            selectedThumbnail = thumbnail
                            _selectedThumbnailProperty.set(thumbnail.thumbnailImage)
                        }
                    }
                }
        }
}
