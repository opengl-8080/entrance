package entrance.view.javafx.window.tag

import entrance.application.tag.RegisterTagService
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.view.javafx.error.Validations
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
    private val registerTagService: RegisterTagService
) {

    internal lateinit var stage: Stage
    
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
        val validations = Validations {
            validation(
                textField = tagNameTextField,
                label = tagNameErrorMessageLabel,
                validator = TagName.Companion::validate
            )

            validation(
                textArea = filterWordTextArea,
                label = filterWordErrorMessageLabel,
                validator = TagFilterWord.Companion::validate
            )
        }
        
        if (validations.validate()) {
            val tagName = TagName(tagNameTextField.text)
            val tagFilterWord = TagFilterWord(filterWordTextArea.text)
            registerTagService.register(tagName, tagFilterWord)

            stage.close()
        }
    }
}