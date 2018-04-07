package entrance.view.javafx.window.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class RegisterTagWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        fxmlLoader.loadWithStage<RegisterTagController>("tag/register-tag.fxml") { stage, controller ->
            stage.initModality(Modality.WINDOW_MODAL)
            stage.title = "タグ新規登録"
            stage.initOwner(owner)

            controller.stage = stage

            stage.showAndWait()
        }
    }
}