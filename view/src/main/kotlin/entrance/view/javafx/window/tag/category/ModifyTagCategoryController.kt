package entrance.view.javafx.window.tag

import entrance.application.tag.ModifyTagCategoryService
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryName
import entrance.view.javafx.error.Validations
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.stage.Stage

@FXPrototypeController
class ModifyTagCategoryController(
    private val modifyTagCategoryService: ModifyTagCategoryService
) {
    
    internal lateinit var stage: Stage

    @FXML
    lateinit var tagCategoryNameErrorMessageLabel: Label
    @FXML
    lateinit var tagCategoryNameTextField: TextField
    
    private lateinit var tagCategory: TagCategory
    
    fun setTag(tagCategory: TagCategory) {
        tagCategoryNameTextField.text = tagCategory.name.value
        this.tagCategory = tagCategory
    }
    
    @FXML
    fun modify() {
        val validations = Validations {
            validation(
                textField = tagCategoryNameTextField,
                label = tagCategoryNameErrorMessageLabel,
                validator = TagCategoryName.Companion::validate
            )
        }
        
        if (validations.validate()) {
            val tagCategoryName = TagCategoryName(tagCategoryNameTextField.text)
            modifyTagCategoryService.modify(tagCategory, tagCategoryName)

            stage.close()
        }
    }
}