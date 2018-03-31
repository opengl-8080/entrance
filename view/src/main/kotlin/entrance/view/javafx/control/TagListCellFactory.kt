package entrance.view.javafx.control

import entrance.domain.tag.Tag
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback


class TagListCellFactory: Callback<ListView<Tag>, ListCell<Tag>> {
    override fun call(param: ListView<Tag>?): ListCell<Tag> =
            object: ListCell<Tag>() {
                override fun updateItem(tag: Tag?, empty: Boolean) {
                    super.updateItem(tag, empty)

                    if (empty || tag == null) {
                        text = null
                        graphic = null
                    } else {
                        text = tag.name.value
                    }
                }
            }
}