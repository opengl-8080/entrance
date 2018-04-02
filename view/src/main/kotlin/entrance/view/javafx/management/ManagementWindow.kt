package entrance.view.javafx.management

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class ManagementWindow(
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        val context = fxmlLoader.load<ManagementController>("management/management.fxml")

        val stage = Stage()
        stage.initOwner(owner)
        stage.initModality(Modality.WINDOW_MODAL)
        val scene = Scene(context.root)
        stage.scene = scene
        
        stage.showAndWait()
    }
}