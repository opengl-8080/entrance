package entrance.view.javafx

import entrance.view.javafx.util.EntranceFXMLLoader
import entrance.view.javafx.viewer.SingleImageViewerWindow
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class MainWindow(
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(primaryStage: Stage) {
        fxmlLoader.load<MainController>("main.fxml") { root, controller ->
            controller.primaryStage = primaryStage

            primaryStage.onCloseRequest = EventHandler {
                SingleImageViewerWindow.closeAll()
            }
            
            primaryStage.scene = Scene(root)
            primaryStage.title = "Entrance"
            primaryStage.show()
        }
    }
}
