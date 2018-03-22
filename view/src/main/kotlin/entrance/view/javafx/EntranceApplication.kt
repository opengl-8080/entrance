package entrance.view.javafx

import javafx.application.Application
import javafx.stage.Stage
import org.springframework.context.ConfigurableApplicationContext

class EntranceApplication: Application() {
    
    companion object {
        var context: ConfigurableApplicationContext? = null
    }
    
    override fun start(primaryStage: Stage) {
        val context = context!!
        
        context.beanFactory.registerSingleton("primaryStage", primaryStage)

        val mainWindow = context.getBean(MainWindow::class.java)
        mainWindow.open(primaryStage)
    }
}