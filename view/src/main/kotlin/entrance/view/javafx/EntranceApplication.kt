package entrance.view.javafx

import entrance.domain.util.error.InvalidValueException
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.extractRootCause
import entrance.view.javafx.window.MainWindow
import javafx.application.Application
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import org.springframework.context.ConfigurableApplicationContext

class EntranceApplication: Application() {
    private val logger = LoggerFactory.getLogger(EntranceApplication::class.java)
    
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
                    logger.error("unknown error", throwable)
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