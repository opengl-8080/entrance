package entrance.view.javafx.window.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class RegisterTagCategoryWindow(
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        fxmlLoader.loadWithStage<RegisterTagCategoryController>("tag/category/register-tag-category.fxml") { stage, controller ->
            stage.initModality(Modality.WINDOW_MODAL)
            stage.title = "タグカテゴリ新規登録"
            stage.initOwner(owner)

            controller.stage = stage

            stage.showAndWait()
        }
    }
}