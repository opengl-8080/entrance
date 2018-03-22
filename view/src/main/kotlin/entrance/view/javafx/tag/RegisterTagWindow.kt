package entrance.view.javafx.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class RegisterTagWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        val fxmlContext = fxmlLoader.load<RegisterTagController>("tag/register-tag.fxml")

        val stage = Stage()
        stage.initModality(Modality.WINDOW_MODAL)
        stage.scene = Scene(fxmlContext.root)
        stage.title = "タグ新規登録"
        stage.initOwner(owner)

        fxmlContext.controller.stage = stage

        stage.showAndWait()
    }
}