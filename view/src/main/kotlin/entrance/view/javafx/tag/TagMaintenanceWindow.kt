package entrance.view.javafx.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class TagMaintenanceWindow (private val fxmlLoader: EntranceFXMLLoader) {
    
    fun open(owner: Stage) {
        val fxmlContext = fxmlLoader.load<TagMaintenanceController>("tag/tag-maintenance.fxml")
        
        val stage = Stage()
        stage.initModality(Modality.WINDOW_MODAL)
        stage.scene = Scene(fxmlContext.root)
        stage.title = "タグメンテナンス"
        stage.initOwner(owner)

        fxmlContext.controller.stage = stage
        
        stage.showAndWait()
    }
}