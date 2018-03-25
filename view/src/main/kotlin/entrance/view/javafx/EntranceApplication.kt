package entrance.view.javafx

import entrance.domain.error.InvalidValueException
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.extractRootCause
import javafx.application.Application
import javafx.stage.Stage
import org.springframework.context.ConfigurableApplicationContext

class EntranceApplication: Application() {
    
    companion object {
        var context: ConfigurableApplicationContext? = null
    }
    
    override fun start(primaryStage: Stage) {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable -> 
            val rootCause = extractRootCause(throwable)
            when (rootCause) {
                is InvalidValueException -> {
                    Dialog.warn(rootCause.message)
                }
                else -> {
                    Dialog.error(rootCause)
                }
            }
        }
        
        val context = context!!
        
        context.beanFactory.registerSingleton("primaryStage", primaryStage)

        val mainWindow = context.getBean(MainWindow::class.java)
        mainWindow.open(primaryStage)
    }
}