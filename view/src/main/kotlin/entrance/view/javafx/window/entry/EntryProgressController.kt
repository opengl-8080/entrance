package entrance.view.javafx.window.entry

import entrance.application.entry.SaveEnteredItemService
import entrance.application.similar.FilterSimilarImageService
import entrance.domain.entry.EntryDirectory
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
    saveEnteredItemService: SaveEnteredItemService,
    filterSimilarImageService: FilterSimilarImageService
) {
    private val logger = LoggerFactory.getLogger(EntryProgressController::class.java)
    
    @FXML
    lateinit var label: Label
    @FXML
    lateinit var progressBar: ProgressBar
    
    private var task =  EntryTask(entryDirectory, saveEnteredItemService, filterSimilarImageService)
    
    fun execute(stage: Stage) {
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
    private val saveEnteredItemService: SaveEnteredItemService,
    private val filterSimilarImageService: FilterSimilarImageService
): Task<Void>() {
    private val logger = LoggerFactory.getLogger(EntryTask::class.java)
    
    override fun call(): Void? {
        val allImages = entryDirectory.readAllImages()
        
        val total = allImages.size.toLong()
        var count = 0L
                
        updateProgress(count, total)

        allImages.forEachImages { entryImage ->
            if (isCancelled) {
                logger.debug("cancel entry task")
                return@forEachImages
            }
            
            if (filterSimilarImageService.decideToSave(entryImage)) {
                saveEnteredItemService.save(entryImage)
            }

            count++
            updateProgress(count, total)
        }
        
        return null
    }

    override fun updateProgress(workDone: Long, max: Long) {
        super.updateProgress(workDone, max)
        updateMessage("読み込み中($workDone/$max)")
    }
}