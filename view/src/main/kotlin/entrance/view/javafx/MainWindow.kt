package entrance.view.javafx

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class MainWindow(
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(primaryStage: Stage) {
        val fxmlContext = fxmlLoader.load<MainController>("main.fxml")
        fxmlContext.controller.primaryStage = primaryStage
        primaryStage.scene = Scene(fxmlContext.root)
        primaryStage.title = "Entrance"
        primaryStage.show()
    }
}