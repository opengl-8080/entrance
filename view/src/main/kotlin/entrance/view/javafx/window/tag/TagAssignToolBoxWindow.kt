package entrance.view.javafx.window.tag

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.springframework.stereotype.Component

@Component
class TagAssignToolBoxWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        fxmlLoader.loadWithStage<TagAssignToolBoxController>("tag/tag-assign-tool-box.fxml") { stage, _ ->
            stage.initOwner(owner)
            stage.isAlwaysOnTop = true
            stage.initStyle(StageStyle.UTILITY)
            stage.show()
            stage.toFront()
        }
    }
}