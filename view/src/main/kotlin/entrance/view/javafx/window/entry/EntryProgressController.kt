package entrance.view.javafx.window.entry

import entrance.application.entry.EntryUseCase
import entrance.domain.entry.entrance.EntryDirectory
import entrance.view.javafx.util.FXPrototypeController
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.slf4j.LoggerFactory

@FXPrototypeController
class EntryProgressController (
    entryDirectory: EntryDirectory,
    entryUsecase: EntryUseCase
) {
    private val logger = LoggerFactory.getLogger(EntryProgressController::class.java)
    
    @FXML
    lateinit var label: Label
    @FXML
    lateinit var progressBar: ProgressBar
    
    private var task =  EntryTask(entryDirectory, entryUsecase)
    
    fun execute(stage: Stage) {
        logger.debug("execute EntryProgressController")
        
        task.onSucceeded = EventHandler { 
            stage.close()
        }
        task.onCancelled = EventHandler { 
            stage.close()
        }
        stage.onCloseRequest = EventHandler { 
            logger.debug("close request")
            task.cancel()
        }

        label.textProperty().bind(task.messageProperty())
        progressBar.progressProperty().bind(task.progressProperty())

        Thread(task).start()
    }
    
    @FXML
    fun cancel() {
        task.cancel()
    }
}

class EntryTask(
    private val entryDirectory: EntryDirectory,
    private val entryUseCase: EntryUseCase
): Task<Void>() {
    private val logger = LoggerFactory.getLogger(EntryTask::class.java)
    
    override fun call(): Void? {
        logger.debug("start EntryTask")
        
        val entryImages = entryDirectory.getEntryImages()
        val entryBooks = entryDirectory.getEntryBooks()
        
        val total = (entryImages.size + entryBooks.size).toLong()
        updateProgress(0L, total)
        
        var count = 0L
        entryUseCase.execute(entryImages, entryBooks) { cancel ->
            count++
            logger.debug("EntryTask progress count={}", count)
            updateProgress(count, total)
            
            if (isCancelled) {
                cancel()
            }
        }
        
        return null
    }
    
    override fun updateProgress(workDone: Long, max: Long) {
        super.updateProgress(workDone, max)
        updateMessage("読み込み中($workDone/$max)")
    }
}