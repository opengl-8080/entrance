package entrance.view.javafx.categorization

import entrance.domain.categorization.CategorizationImageUnit
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class CategorizeTagWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage, imageUnit: CategorizationImageUnit) {
        val context = fxmlLoader.load<CategorizeTagController>("categorization/categorize-tag.fxml")
        
        val stage = Stage()
        stage.initOwner(owner)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.title = "タグ割り当て"
        stage.scene = Scene(context.root)
        
        context.controller.init(stage, imageUnit)
        
        stage.showAndWait()
    }
}