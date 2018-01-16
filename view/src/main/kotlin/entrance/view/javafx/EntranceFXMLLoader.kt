package entrance.view.javafx

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class EntranceFXMLLoader(private val context: ApplicationContext) {
    
    fun <T> showAsModal(path: String, owner: Stage? = null): FxmlContext<T> {
        val fxmlContext = load<T>(path)

        val scene = Scene(fxmlContext.root)
        val stage = Stage()

        val controller = fxmlContext.controller
        if (controller is InjectOwnStage) {
            controller.ownStage = stage
        }
        
        stage.scene = scene
        if (owner == null) {
            stage.initOwner(context.getBean("primaryStage", Stage::class.java))
        } else {
            stage.initOwner(owner)
        }
        stage.initModality(Modality.WINDOW_MODAL)
        stage.showAndWait()
        
        return fxmlContext
    }

    fun <T> load(path: String): FxmlContext<T> {
        val fxmlUrl = EntranceFXMLLoader::class.java.getResource("/fxml/$path")
        val loader = FXMLLoader(fxmlUrl)
        loader.controllerFactory = Callback<Class<*>, Any> { this.context.getBean(it) }

        val root: Parent = loader.load<Parent>()
        val controller = loader.getController<T>()
        return FxmlContext(root = root, controller = controller)
    }
}
