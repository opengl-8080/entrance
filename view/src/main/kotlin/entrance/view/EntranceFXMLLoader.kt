package entrance.view

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.UncheckedIOException

@Component
class EntranceFXMLLoader(private val context: ApplicationContext) {

    fun load(path: String): Parent {
        val loader = FXMLLoader()
        loader.controllerFactory = Callback<Class<*>, Any> { this.context.getBean(it) }
        
        try {
            return loader.load(EntranceFXMLLoader::class.java.getResourceAsStream("/fxml/$path"))
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }
}
