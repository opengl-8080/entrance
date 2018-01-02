package entrance.view

import entrance.db.ItemDao
import javafx.fxml.Initializable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.net.URL
import java.util.ResourceBundle

@Component
class MainController : Initializable {
    @Autowired
    lateinit var dao: ItemDao

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        println("Hello")
        var all = this.dao.selectAll()
        println("all=$all")
    }
}
