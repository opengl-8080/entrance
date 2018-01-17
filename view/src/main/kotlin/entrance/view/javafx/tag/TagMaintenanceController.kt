package entrance.view.javafx.tag

import entrance.view.javafx.EntranceFXMLLoader
import entrance.view.javafx.InjectOwnStage
import entrance.view.javafx.StageTitle
import javafx.fxml.FXML
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class TagMaintenanceController (
    private val fxmlLoader: EntranceFXMLLoader
): InjectOwnStage, StageTitle {
    override lateinit var ownStage: Stage
    override val title = "タグメンテナンス"
    
    @FXML
    fun add() {
        fxmlLoader.showAsModal<RegisterTagController>("register-tag.fxml", owner = ownStage)
    }
}
