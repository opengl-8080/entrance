package entrance.view.javafx.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class CategorizeTagWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage, imageUnit: CategorizationImageUnit, onSaved: () -> Unit) {

        fxmlLoader.loadWithStage<CategorizeTagController>("categorization/categorize-tag.fxml") { stage, controller ->
            stage.initOwner(owner)
            stage.initModality(Modality.WINDOW_MODAL)
            stage.title = "タグ割り当て"

            controller.init(stage, imageUnit, onSaved)

            stage.showAndWait()
        }
    }
}