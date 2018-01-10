package entrance.view.javafx

import entrance.domain.config.EntranceHome
import javafx.fxml.Initializable
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val entranceHome: EntranceHome
) : Initializable {
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        println(entranceHome.path())
    }
}
