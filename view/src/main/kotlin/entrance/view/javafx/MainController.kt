package entrance.view.javafx

import javafx.fxml.FXML
import javafx.fxml.Initializable
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val fxmlLoader: EntranceFXMLLoader
) : Initializable {
    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }
    
    @FXML
    fun openTagMaintenance() {
        fxmlLoader.showAsModal<Any>("tag/tag-maintenance.fxml")
    }
}
