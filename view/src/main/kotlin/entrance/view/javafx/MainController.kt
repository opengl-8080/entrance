package entrance.view.javafx

import entrance.view.javafx.categorization.CategorizationWindow
import entrance.view.javafx.tag.TagMaintenanceWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val tagMaintenanceWindow: TagMaintenanceWindow,
    private val categorizationWindow: CategorizationWindow
) : Initializable {
    
    lateinit internal var primaryStage: Stage
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }
    
    @FXML
    fun openTagMaintenance() {
        tagMaintenanceWindow.open(primaryStage)
    }
    
    @FXML
    fun openCategorizationWindow() {
        categorizationWindow.open(primaryStage)
    }
}
