package entrance.view.javafx.tag

import entrance.application.tag.RegisterTagService
import entrance.domain.error.ErrorCodeCollector
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagFilterWordError
import entrance.domain.tag.TagName
import entrance.domain.tag.TagNameError
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
        tagNameTextField.styleClass.remove("error")
        filterWordTextArea.styleClass.remove("error")
        
        val errorCodeList = ErrorCodeCollector().collect(
            { TagName.validate(tagNameTextField.text) },
            { TagFilterWord.validate(filterWordTextArea.text) }
        )
        
        if (errorCodeList.isEmpty()) {
            val tagName = TagName(tagNameTextField.text)
            val tagFilterWord = TagFilterWord(filterWordTextArea.text)
            registerTagService.register(tagName, tagFilterWord)

            ownStage.close()
        } else {
            errorCodeList.forEach { errorCode ->
                when (errorCode) {
                    is TagNameError -> {
                        tagNameTextField.styleClass.add("error")
                        println(errorCode.getMessage())
                    }
                    is TagFilterWordError -> {
                        filterWordTextArea.styleClass.add("error")
                        println(errorCode.getMessage())
                    }
                }
            }
        }
    }
}