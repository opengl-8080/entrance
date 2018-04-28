package entrance.view.javafx.window.tag

import entrance.application.tag.ModifyTagService
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.error.Validations
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage
import javafx.util.StringConverter

@FXPrototypeController
class ModifyTagController (
    private val modifyTagService: ModifyTagService,
    private val tagCategoryRepository: TagCategoryRepository
) {
    
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
    
    private lateinit var tag: Tag
    
    fun setTag(tag: Tag) {
        tagNameTextField.text = tag.name.value
        filterWordTextArea.text = tag.filterWord.value
        this.tag = tag

        val allTagCategories = tagCategoryRepository.findAll()
        tagCategoryChoiceBox.items.addAll(allTagCategories.tagCategories)
        tagCategoryChoiceBox.selectionModel.select(allTagCategories.get(tag.tagCategory.name))
        tagCategoryChoiceBox.converter = object: StringConverter<TagCategory>() {
            override fun fromString(name: String): TagCategory = allTagCategories.get(name)
            override fun toString(tagCategory: TagCategory): String = tagCategory.name.value
        }
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
            val tagCategory = tagCategoryChoiceBox.selectionModel.selectedItem
            modifyTagService.modify(tag, tagName, tagFilterWord, tagCategory)

            stage.close()
        }
    }
}