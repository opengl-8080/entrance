package entrance.view.javafx.window.entry

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.springframework.stereotype.Component

@Component
class EntryProgressWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage) {
        fxmlLoader.loadWithStage<EntryProgressController>("entry/entry-progress.fxml") { stage, controller ->
            stage.initOwner(owner)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.initStyle(StageStyle.UTILITY)
            stage.title = "エントリ画像ロード"
            stage.isResizable = false
            stage.show()
            
            controller.execute(stage)
        }
    }
}