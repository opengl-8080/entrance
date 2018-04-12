package entrance.view.javafx.window.viewer

import entrance.domain.viewer.StoredImage
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
    
    fun open(selectedStoredImage: StoredImage, storedImageList: List<StoredImage>) {
        fxmlLoader.loadWithStage<SingleImageViewerController>("viewer/single-image.fxml") { stage, controller ->
            controller.init(stage, selectedStoredImage, storedImageList)

            stage.fullScreenExitHint = ""
            openedStages += stage
            stage.show()
        }
    }
}
