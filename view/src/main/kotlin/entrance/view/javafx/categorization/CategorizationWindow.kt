package entrance.view.javafx.categorization

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class CategorizationWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    fun open(owner: Stage) {
        fxmlLoader.loadWithStage<CategorizationController>("categorization/categorization.fxml") { stage, controller ->
            stage.title = "分類"
            stage.initOwner(owner)

            controller.ownStage = stage

            stage.showAndWait()
        }
    }
}