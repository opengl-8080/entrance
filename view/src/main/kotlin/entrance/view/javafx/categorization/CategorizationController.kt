package entrance.view.javafx.categorization

import entrance.domain.categorization.NotCategorizedImageRepository
import javafx.fxml.Initializable
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
@Scope("prototype")
class CategorizationController (
    private val notCategorizedImageRepository: NotCategorizedImageRepository
): Initializable {
    internal lateinit var stage: Stage

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        
    }
}
