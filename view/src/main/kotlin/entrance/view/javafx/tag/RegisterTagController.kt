package entrance.view.javafx.tag

import entrance.application.tag.RegisterTagService
import entrance.domain.error.ErrorMessageCollector
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.view.javafx.EntranceFXMLLoader
import entrance.view.javafx.InjectOwnStage
import entrance.view.javafx.StageTitle
import javafx.fxml.FXML
import javafx.scene.control.Label
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
    lateinit var tagNameErrorMessageLabel: Label
    @FXML
    lateinit var tagNameTextField: TextField
    @FXML
    lateinit var filterWordErrorMessageLabel: Label
    @FXML
    lateinit var filterWordTextArea: TextArea
    
    @FXML
    fun register() {
        tagNameTextField.styleClass.remove("error")
        tagNameErrorMessageLabel.text = ""
        filterWordTextArea.styleClass.remove("error")
        filterWordErrorMessageLabel.text = ""

        val errorMessageCollector = ErrorMessageCollector()
        errorMessageCollector.collect(TagName.validate(tagNameTextField.text))
        errorMessageCollector.collect(TagFilterWord.validate(filterWordTextArea.text))
        
        if (errorMessageCollector.hasError()) {
            errorMessageCollector.forEach {
                when (it.type) {
                    TagName.ERROR -> {
                        tagNameTextField.styleClass.add("error")
                        tagNameErrorMessageLabel.text = it.message
                    }
                    TagFilterWord.ERROR -> {
                        filterWordTextArea.styleClass.add("error")
                        filterWordErrorMessageLabel.text = it.message
                    }
                }
            }
        } else {
            val tagName = TagName(tagNameTextField.text)
            val tagFilterWord = TagFilterWord(filterWordTextArea.text)
            registerTagService.register(tagName, tagFilterWord)

            ownStage.close()
        }
    }
}