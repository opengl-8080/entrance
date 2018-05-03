package entrance.view.javafx.control

import entrance.domain.ImageFile
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton

/**
 * サムネイル画像のビュー.
 */
class ThumbnailView<out T: ImageFile>(
    val imageFile: T
): ImageView(Image(imageFile.thumbnailUri.toString())) {
    private val selectedCssClass = "thumbnail--selected"
    
    /**
     * このサムネイルが選択されているかどうかのフラグ.
     */
    var selected: Boolean = false
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

    /**
     * このサムネイルの選択状態を切り替える.
     */
    private fun switchSelect() {
        if (selected) {
            deselect()
        } else {
            select()
        }
    }

    /**
     * このサムネイルを選択状態にする.
     */
    fun select() {
        if (selected) {
            return
        }

        styleClass.add(selectedCssClass)
        selected = true
    }

    /**
     * このサムネイルの選択状態を解除する.
     */
    fun deselect() {
        if (!selected) {
            return
        }

        styleClass.remove(selectedCssClass)
        selected = false
    }
}