package entrance.view.javafx

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.context.ConfigurableApplicationContext

class EntranceApplication: Application() {
    
    companion object {
        var context: ConfigurableApplicationContext? = null
    }
    
    override fun start(primaryStage: Stage) {
        val context = context!!
        
        context.beanFactory.registerSingleton("primaryStage", primaryStage)
        
        val loader = context.getBean(EntranceFXMLLoader::class.java)
        val fxmlContext = loader.load<Any>("main.fxml")
        val scene = Scene(fxmlContext.root)
        primaryStage.title = "Entrance"
        primaryStage.scene = scene
        primaryStage.show()
    }
}