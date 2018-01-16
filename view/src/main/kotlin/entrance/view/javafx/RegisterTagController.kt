package entrance.view.javafx

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
@Scope("prototype")
class RegisterTagController (
    private val fxmlLoader: EntranceFXMLLoader
): Initializable {
    @FXML
    lateinit var addKanaButton: Button
    @FXML
    lateinit var tagNameTextField: TextField
    @FXML
    lateinit var kanaTextField: TextField
    @FXML
    lateinit var kanaListView: ListView<String>
    @FXML
    lateinit var removeKanaMenuItem: MenuItem
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        addKanaButton.defaultButtonProperty().bind(addKanaButton.focusedProperty())
    }
    
    @FXML
    fun addKana() {
        val kana = kanaTextField.text
        kanaListView.items.add(kana)
    }
    
    @FXML
    fun onShownListViewContextMenu() {
        
    }
    
    @FXML
    fun register() {
        
    }
}