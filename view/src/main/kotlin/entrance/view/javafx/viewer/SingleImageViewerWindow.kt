package entrance.view.javafx.viewer

import entrance.domain.image.Image
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class SingleImageViewerWindow(
    private val fxmlLoader: EntranceFXMLLoader
) {
    companion object {
        private val openedStages = mutableSetOf<Stage>()
        
        fun closeAll() {
            openedStages.forEach { it.close() }
        }
    }
    
    fun open(selectedImage: Image, imageList: List<Image>) {
        val context = fxmlLoader.load<SingleImageViewerController>("viewer/single-image.fxml")
        
        val stage = Stage()
        context.controller.init(stage, selectedImage, imageList)
        
        stage.fullScreenExitHint = ""
        openedStages += stage
        val scene = Scene(context.root)
        stage.scene = scene
        stage.show()
    }

}
