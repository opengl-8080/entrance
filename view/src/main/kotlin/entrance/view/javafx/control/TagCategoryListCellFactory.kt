package entrance.view.javafx.control

import entrance.domain.tag.category.TagCategory
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback


class TagCategoryListCellFactory : Callback<ListView<TagCategory>, ListCell<TagCategory>> {
    override fun call(param: ListView<TagCategory>?): ListCell<TagCategory> =
            object: ListCell<TagCategory>() {
                override fun updateItem(tagCategory: TagCategory?, empty: Boolean) {
                    super.updateItem(tagCategory, empty)

                    if (empty || tagCategory == null) {
                        text = null
                        graphic = null
                    } else {
                        text = tagCategory.name.value
                    }
                }
            }
}