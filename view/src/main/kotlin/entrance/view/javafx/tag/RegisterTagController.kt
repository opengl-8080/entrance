package entrance.view.javafx.tag

import entrance.view.javafx.EntranceFXMLLoader
import entrance.view.javafx.InjectOwnStage
import entrance.view.javafx.StageTitle
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class RegisterTagController (
    private val fxmlLoader: EntranceFXMLLoader
): InjectOwnStage, StageTitle {
    override lateinit var ownStage: Stage
    override val title = "タグ新規登録"
    
    @FXML
    lateinit var tagNameTextField: TextField
    @FXML
    lateinit var kanaTextArea: TextArea
    
    @FXML
    fun register() {
        ownStage.close()
    }
}