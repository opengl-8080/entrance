package entrance.view.javafx.window.tag

import entrance.application.tag.RegisterTagCategoryService
import entrance.domain.tag.category.TagCategoryName
import entrance.view.javafx.error.Validations
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage

@FXPrototypeController
class RegisterTagCategoryController(
    private val registerTagCategoryService: RegisterTagCategoryService
) {

    internal lateinit var stage: Stage
    
    @FXML
    lateinit var tagCategoryNameErrorMessageLabel: Label
    @FXML
    lateinit var tagCategoryNameTextField: TextField
    
    @FXML
    fun register() {
        val validations = Validations {
            validation(
                textField = tagCategoryNameTextField,
                label = tagCategoryNameErrorMessageLabel,
                validator = TagCategoryName.Companion::validate
            )
        }
        
        if (validations.validate()) {
            val tagCategoryName = TagCategoryName(tagCategoryNameTextField.text)
            registerTagCategoryService.register(tagCategoryName)

            stage.close()
        }
    }
}