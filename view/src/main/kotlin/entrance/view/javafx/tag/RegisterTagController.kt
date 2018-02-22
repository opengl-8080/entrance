package entrance.view.javafx.tag

import entrance.application.tag.RegisterTagService
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
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
    private val fxmlLoader: EntranceFXMLLoader,
    private val registerTagService: RegisterTagService
): InjectOwnStage, StageTitle {
    override lateinit var ownStage: Stage
    override val title = "タグ新規登録"
    
    @FXML
    lateinit var tagNameTextField: TextField
    @FXML
    lateinit var filterWordTextArea: TextArea
    
    @FXML
    fun register() {
        val tagName = TagName(tagNameTextField.text)
        val tagFilterWord = TagFilterWord(filterWordTextArea.text)
        registerTagService.register(tagName, tagFilterWord)
        
        ownStage.close()
    }
}