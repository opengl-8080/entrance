package entrance.view

import javafx.fxml.Initializable
import org.springframework.stereotype.Component

import java.net.URL
import java.util.ResourceBundle

@Component
class MainController : Initializable {

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        println("Hello")
    }
}
