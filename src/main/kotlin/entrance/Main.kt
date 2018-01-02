package entrance

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext

private var context: ConfigurableApplicationContext? = null

fun main(args: Array<String>) {
    context = SpringApplication.run(Main::class.java, *args)
    Application.launch(Main::class.java, *args)
}

@SpringBootApplication
class Main : Application() {
    
    override fun start(primaryStage: Stage) {
        val loader = context!!.getBean(SpringFXMLLoader::class.java)
        val root = loader.load("fxml/main.fxml")
        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.show()
    }

    override fun stop() {
        context!!.close()
    }
}
