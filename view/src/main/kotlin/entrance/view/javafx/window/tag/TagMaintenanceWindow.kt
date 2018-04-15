package entrance.view.javafx.window.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class TagMaintenanceWindow (private val fxmlLoader: EntranceFXMLLoader) {
    
    private var opened = false
    
    fun open(owner: Stage) {
        if (opened) {
            return
        }
        fxmlLoader.loadWithStage<TagMaintenanceController>("tag/tag-maintenance.fxml") { stage, controller ->
            stage.title = "タグメンテナンス"
            stage.initOwner(owner)

            controller.stage = stage
            opened = true

            stage.showAndWait()
            
            opened = false
        }
    }
}