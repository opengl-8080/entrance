package entrance.view.javafx

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream
import java.io.UncheckedIOException
import java.nio.file.Paths

@Component
class EntranceFXMLLoader(private val context: ApplicationContext) {
    
    fun <T> showAsModal(path: String): FxmlContext<T> {
        val fxmlContext = load<T>(path)

        val scene = Scene(fxmlContext.root)
        val stage = Stage()
        stage.scene = scene
        stage.initOwner(context.getBean("primaryStage", Stage::class.java))
        stage.initModality(Modality.WINDOW_MODAL)
        stage.showAndWait()
        
        return fxmlContext
    }

    fun <T> load(path: String): FxmlContext<T> {
        val loader = FXMLLoader()
        loader.controllerFactory = Callback<Class<*>, Any> { this.context.getBean(it) }
        var inputStream: InputStream? = null
        
        try {
            val fxmlUrl = EntranceFXMLLoader::class.java.getResource("/fxml/$path")
            val fxmlPath = Paths.get(fxmlUrl.toURI())
            val location = fxmlPath.parent
            loader.location = location.toUri().toURL()

            inputStream = fxmlUrl.openStream()
            val root: Parent = loader.load<Parent>(inputStream)
            val controller = loader.getController<T>()
            return FxmlContext(root = root, controller = controller)
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
    }
}
