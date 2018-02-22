package entrance.view.javafx.tag

import entrance.domain.tag.TagRepository
import entrance.view.javafx.EntranceFXMLLoader
import entrance.view.javafx.InjectOwnStage
import entrance.view.javafx.StageTitle
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class TagMaintenanceController (
    private val fxmlLoader: EntranceFXMLLoader,
    private val tagRepository: TagRepository
): InjectOwnStage, StageTitle, Initializable {
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        loadTags()
    }

    override lateinit var ownStage: Stage
    override val title = "タグメンテナンス"
    
    @FXML
    lateinit var tagListView: ListView<String>
    
    @FXML
    fun add() {
        fxmlLoader.showAsModal<RegisterTagController>("register-tag.fxml", owner = ownStage)
        loadTags()
    }
    
    private fun loadTags() {
        tagListView.items.clear()
        
        tagRepository.findAll().tags.forEach { tag ->
            tagListView.items.add(tag.name.value)
        }
    }
}
