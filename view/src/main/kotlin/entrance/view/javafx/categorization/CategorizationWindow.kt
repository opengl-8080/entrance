package entrance.view.javafx.categorization

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class CategorizationWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    fun open(owner: Stage) {
        val fxmlContext = fxmlLoader.load<CategorizationController>("categorization/categorization.fxml")

        val stage = Stage()
        stage.scene = Scene(fxmlContext.root)
        stage.title = "分類"
        stage.initOwner(owner)

        fxmlContext.controller.ownStage = stage

        stage.showAndWait()
    }
}