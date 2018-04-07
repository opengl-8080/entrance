package entrance.view.javafx.util

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class EntranceFXMLLoader(private val context: ApplicationContext) {

    fun <T> load(path: String, block: (root: Parent, controller: T) -> Unit) {
        val fxmlUrl = EntranceFXMLLoader::class.java.getResource("/fxml/$path")
        val loader = FXMLLoader(fxmlUrl)
        loader.controllerFactory = Callback<Class<*>, Any> { this.context.getBean(it) }

        val root: Parent = loader.load<Parent>()
        val controller = loader.getController<T>()
        
        block(root, controller)
    }

    fun <T> loadWithStage(path: String, block: (stage: Stage, controller: T) -> Unit) {
        load<T>(path) { root, controller ->
            val stage = Stage()
            stage.scene = Scene(root)

            block(stage, controller)
        }
    }
}
