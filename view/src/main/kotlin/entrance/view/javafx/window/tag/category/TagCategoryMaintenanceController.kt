package entrance.view.javafx.window.tag.category

import entrance.application.tag.category.DeleteTagCategoryService
import entrance.domain.tag.TagRepository
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.control.TagCategoryListCellFactory
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.tag.ModifyTagCategoryWindow
import entrance.view.javafx.window.tag.RegisterTagCategoryWindow
import javafx.beans.binding.BooleanBinding
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*
import java.util.function.Predicate

@FXPrototypeController
class TagCategoryMaintenanceController (
    private val registerTagCategoryWindow: RegisterTagCategoryWindow,
    private val modifyTagCategoryWindow: ModifyTagCategoryWindow,
    private val deleteTagCategoryService: DeleteTagCategoryService,
    private val tagCategoryRepository: TagCategoryRepository,
    private val tagRepository: TagRepository
): Initializable {
    lateinit internal var stage: Stage

    @FXML
    lateinit var filterTextFiled: TextField
    @FXML
    lateinit var tagCategoryListView: ListView<TagCategory>
    @FXML
    lateinit var modifyMenuItem: MenuItem
    @FXML
    lateinit var deleteMenuItem: MenuItem

    private val tagCategoryListItems = FXCollections.observableArrayList<TagCategory>()
    lateinit var filteredTagListItems: FilteredList<TagCategory>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        filteredTagListItems = tagCategoryListItems.filtered {true}
        tagCategoryListView.items = filteredTagListItems
        tagCategoryListView.cellFactory = TagCategoryListCellFactory()

        loadTagCategories()
        filterTextFiled.textProperty().addListener {_, _, inputText ->
            filteredTagListItems.predicate = Predicate { it.name.matches(inputText) }
        }
        
        val unmodifiableTagCategory = object: BooleanBinding() {
            init {
                bind(tagCategoryListView.selectionModel.selectedItemProperty())
            }

            override fun computeValue(): Boolean {
                val selectedTagCategory = tagCategoryListView.selectionModel.selectedItem
                return selectedTagCategory?.isUnmodifiable() ?: false
            }
        }

        modifyMenuItem.disableProperty().bind(unmodifiableTagCategory)
        deleteMenuItem.disableProperty().bind(unmodifiableTagCategory)
    }

    @FXML
    fun add() {
        registerTagCategoryWindow.open(stage)
        loadTagCategories()
    }

    @FXML
    fun modify() {
        val selectedTagCategory = tagCategoryListView.selectionModel.selectedItem
        if (selectedTagCategory != null) {
            modifyTagCategoryWindow.open(stage, selectedTagCategory.name)
            loadTagCategories()
        }
    }

    @FXML
    fun remove() {
        val selectedTagCategory = tagCategoryListView.selectionModel.selectedItem
        if (selectedTagCategory != null) {
            val usedTagList = tagRepository.findByTagCategoryForUpdate(selectedTagCategory)

            val message = if (usedTagList.isNotEmpty()) {
                """「${selectedTagCategory.name.value}」はタグで使用されています。
                |このカテゴリを使用しているタグは、カテゴリが強制的に「その他」になります。
                |削除してよろしいですか？""".trimMargin()
                
            } else {
                "タグカテゴリを削除します。よろしいですか？"
            }
            
            if (!Dialog.confirm(message)) {
                return
            }

            deleteTagCategoryService.delete(selectedTagCategory)
            
            loadTagCategories()
        }
    }

    private fun loadTagCategories() {
        tagCategoryListItems.clear()

        tagCategoryRepository.findAll().tagCategories.forEach { tag ->
            tagCategoryListItems.add(tag)
        }
    }
}
