package entrance.view.javafx

import entrance.domain.ImageRepository
import javafx.fxml.Initializable
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val imageRepository: ImageRepository
) : Initializable {
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        println(imageRepository)
    }
}
