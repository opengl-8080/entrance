package entrance.view.javafx.window.viewer

import entrance.domain.image.Image
import entrance.view.javafx.util.EntranceFXMLLoader
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
        fxmlLoader.loadWithStage<SingleImageViewerController>("viewer/single-image.fxml") { stage, controller ->
            controller.init(stage, selectedImage, imageList)

            stage.fullScreenExitHint = ""
            openedStages += stage
            stage.show()
        }
    }
}
