package entrance.view.javafx.window.tag

import entrance.application.tag.ModifyTagService
import entrance.domain.tag.Tag
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
class ModifyTagController (
    private val modifyTagService: ModifyTagService
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
    
    private lateinit var tag: Tag
    
    fun setTag(tag: Tag) {
        tagNameTextField.text = tag.name.value
        filterWordTextArea.text = tag.filterWord.value
        this.tag = tag
    }
    
    @FXML
    fun modify() {
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
            modifyTagService.modify(tag, tagName, tagFilterWord)

            stage.close()
        }
    }
}