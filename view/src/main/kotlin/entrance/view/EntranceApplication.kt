package entrance.view

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.context.ApplicationContext

class EntranceApplication: Application() {
    
    companion object {
        var context: ApplicationContext? = null
    }
    
    override fun start(primaryStage: Stage) {
        val loader = context!!.getBean(EntranceFXMLLoader::class.java)
        val root = loader.load("main.fxml")
        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.show()
    }
}