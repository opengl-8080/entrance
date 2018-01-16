package entrance.view.javafx

import javafx.fxml.FXML
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class TagMaintenanceController (
    private val fxmlLoader: EntranceFXMLLoader
): InjectOwnStage {
    override lateinit var ownStage: Stage
    
    @FXML
    fun add() {
        fxmlLoader.showAsModal<RegisterTagController>("register-tag.fxml", owner = ownStage)
    }
}
