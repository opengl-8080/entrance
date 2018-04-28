package entrance.view.javafx.window.tag

import entrance.application.tag.RegisterTagService
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryName
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.error.Validations
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage
import javafx.util.StringConverter
import java.net.URL
import java.util.*

@FXPrototypeController
class RegisterTagController (
    private val tagCategoryRepository: TagCategoryRepository,
    private val registerTagService: RegisterTagService
): Initializable {
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val allTagCategories = tagCategoryRepository.findAll()
        
        tagCategoryChoiceBox.items.addAll(allTagCategories.tagCategories)
        tagCategoryChoiceBox.converter = object: StringConverter<TagCategory>() {
            override fun fromString(name: String): TagCategory = allTagCategories.get(name)
            override fun toString(tagCategory: TagCategory): String = tagCategory.name.value
        }
        tagCategoryChoiceBox.value = allTagCategories.get(TagCategoryName.OTHERS)
    }

    internal lateinit var stage: Stage
    
    @FXML
    lateinit var tagNameErrorMessageLabel: Label
    @FXML
    lateinit var tagNameTextField: TextField
    @FXML
    lateinit var tagCategoryChoiceBox: ChoiceBox<TagCategory>
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
            val tagCategory = tagCategoryChoiceBox.selectionModel.selectedItem
            registerTagService.register(tagName, tagFilterWord, tagCategory)

            stage.close()
        }
    }
}