package entrance.view.javafx.util

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class EntranceFXMLLoader(private val context: ApplicationContext) {

    fun <T> load(path: String): FxmlContext<T> {
        val fxmlUrl = EntranceFXMLLoader::class.java.getResource("/fxml/$path")
        val loader = FXMLLoader(fxmlUrl)
        loader.controllerFactory = Callback<Class<*>, Any> { this.context.getBean(it) }

        val root: Parent = loader.load<Parent>()
        val controller = loader.getController<T>()
        return FxmlContext(root = root, controller = controller)
    }
}
