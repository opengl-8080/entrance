package entrance.view.javafx.window.categorization

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class CategorizationWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    private var opened = false
    
    fun open(owner: Stage) {
        if (opened) {
            return
        }
        
        fxmlLoader.loadWithStage<CategorizationController>("categorization/categorization.fxml") { stage, controller ->
            stage.title = "分類"
            stage.initOwner(owner)
            
            controller.init(stage)

            opened = true
            stage.showAndWait()
            opened = false
        }
    }
}