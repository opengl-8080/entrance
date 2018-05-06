package entrance.view.javafx.component

import entrance.domain.ItemType
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup

@FXPrototypeController
class ItemTypeSelectController {

    @FXML
    lateinit var itemTypeGroup: ToggleGroup
    @FXML
    lateinit var imageRadioButton: RadioButton
    @FXML
    lateinit var bookRadioButton: RadioButton
    
    val itemType: ItemType
        get() = when(itemTypeGroup.selectedToggle) {
            imageRadioButton -> ItemType.IMAGE
            bookRadioButton -> ItemType.BOOK
            else -> throw Exception("unknown type = ${itemTypeGroup.selectedToggle}")
        }
}
