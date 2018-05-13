package entrance.view.javafx.component

import entrance.domain.sort.TagCategorySortComparator
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.util.FXPrototypeController
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseButton
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import java.net.URL
import java.util.*

@FXPrototypeController
class TagCategorySortController(
    private val tagCategoryRepository: TagCategoryRepository
): Initializable {
    
    
    @FXML
    lateinit var root: VBox
    
    @FXML
    lateinit var firstTagCategoryChoiceBox: ChoiceBox<TagCategory?>
    @FXML
    lateinit var secondTagCategoryChoiceBox: ChoiceBox<TagCategory?>
    @FXML
    lateinit var thirdTagCategoryChoiceBox: ChoiceBox<TagCategory?>
    
    private var tagCategories = mutableListOf<TagCategory?>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initChoiceBoxes()
        reload()
        initContextMenu()
    }
    
    private fun initChoiceBoxes() {
        val converter = object: StringConverter<TagCategory?>() {
            override fun toString(tagCategory: TagCategory?): String? = tagCategory?.name?.value
            override fun fromString(value: String): TagCategory? = tagCategories.firstOrNull { it?.name?.value == value }
        }

        firstTagCategoryChoiceBox.converter = converter
        secondTagCategoryChoiceBox.converter = converter
        thirdTagCategoryChoiceBox.converter = converter
    }
    
    private fun initContextMenu() {
        val contextMenu = ContextMenu()
        val item = MenuItem("再読み込み")
        item.onAction = EventHandler {
            reload()
        }
        contextMenu.items += item

        root.onMouseClicked = EventHandler { e ->
            if (e.button != MouseButton.SECONDARY) {
                contextMenu.hide()
                return@EventHandler
            }

            contextMenu.show(root, e.screenX, e.screenY)
        }
    }
    
    private fun reload() {
        tagCategories = mutableListOf(null)
        tagCategories.addAll(tagCategoryRepository.findAll().tagCategories)

        firstTagCategoryChoiceBox.items.clear()
        secondTagCategoryChoiceBox.items.clear()
        thirdTagCategoryChoiceBox.items.clear()
        
        firstTagCategoryChoiceBox.items.addAll(tagCategories)
        secondTagCategoryChoiceBox.items.addAll(tagCategories)
        thirdTagCategoryChoiceBox.items.addAll(tagCategories)
    }

    val comparator: TagCategorySortComparator
        get() = TagCategorySortComparator(
            first = firstTagCategoryChoiceBox.value,
            second = secondTagCategoryChoiceBox.value,
            third = thirdTagCategoryChoiceBox.value
        )
}