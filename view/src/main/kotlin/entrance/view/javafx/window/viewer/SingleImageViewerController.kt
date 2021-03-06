package entrance.view.javafx.window.viewer

import entrance.domain.image.ImageFile
import entrance.view.javafx.util.FXPrototypeController
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.ProgressBar
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.util.function.BiConsumer
import java.util.function.Consumer
import javafx.scene.image.Image as JavaFxImage

@FXPrototypeController
class SingleImageViewerController {
    @FXML
    lateinit var root: Pane
    @FXML
    lateinit var imageView: ImageView
    @FXML
    lateinit var loadingImageProgressBar: ProgressBar
    
    lateinit var imageViewModel: ImageViewModel
    private val contextMenu = ContextMenu()
    
    private val mouseGesture = MouseGesture()
    private var rightClicked: Boolean = false
    private var changingPage: Boolean = false
    
    fun init(stage: Stage, imageFile: ImageFile, imageList: List<ImageFile>) {
        createContextMenu(stage)
        
        stage.width = 1000.0
        stage.height = 600.0

        imageView.fitWidthProperty().bind(root.widthProperty())
        imageView.fitHeightProperty().bind(root.heightProperty())

        imageViewModel = ImageViewModel(imageView, loadingImageProgressBar, imageList, imageFile)
        
        initGestureHandlers()
    }
    
    private fun createContextMenu(stage: Stage) {
        addMenuItem("全画面", EventHandler { stage.isFullScreen = !stage.isFullScreen })
        addMenuItem("拡大リセット", EventHandler { imageViewModel.reset() })
        contextMenu.items.add(SeparatorMenuItem())
        addMenuItem("閉じる", EventHandler { stage.close() })
    }

    private fun addMenuItem(text: String, handler: EventHandler<ActionEvent>) {
        val item = MenuItem(text)
        item.onAction = handler
        contextMenu.items.add(item)
    }

    fun initGestureHandlers() {
        mouseGesture.bind(root)

        mouseGesture.onMousePressed(Consumer { e ->
            contextMenu.hide()

            rightClicked = e.isSecondaryButtonDown
        })

        mouseGesture.onMouseReleased(Consumer { e ->
            contextMenu.hide()

            if (rightClicked && !changingPage) {
                contextMenu.show(root, e.screenX, e.screenY)
            }

            rightClicked = false
            changingPage = false
        })

        mouseGesture.onLeftDragged(BiConsumer { dx, dy ->
            if (imageViewModel.isZoomed()) {
                imageViewModel.translate(dx, dy)
            }
        })

        mouseGesture.onRightScrolled(Consumer { delta ->
            if (delta < 0) {
                imageViewModel.loadPreviousImage()
            } else {
                imageViewModel.loadNextImage()
            }
            changingPage = true
        })

        mouseGesture.onScrolled(Consumer { delta ->
            if (delta < 0) {
                imageViewModel.zoomOut()
            } else {
                imageViewModel.zoomIn()
            }
            imageViewModel.finishZoom()
        })
    }
}
