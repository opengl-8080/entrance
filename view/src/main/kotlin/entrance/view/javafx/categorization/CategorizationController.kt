package entrance.view.javafx.categorization

import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.view.javafx.control.Thumbnail
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.layout.FlowPane
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
    
    @FXML
    lateinit var notCategorizedImagesPane: FlowPane

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        notCategorizedImageRepository
            .loadAll()
            .forEach {
                notCategorizedImagesPane.children.add(Thumbnail(Image(it.uriString)))
            }
    }
}
