package entrance.view.javafx.window.similar

import entrance.domain.entry.entrance.EntryImage
import entrance.domain.entry.similar.SimilarImageHandleResult
import entrance.domain.entry.similar.SimilarImageHandler
import entrance.domain.entry.similar.SimilarImages
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.application.Platform
import javafx.stage.Modality
import org.springframework.stereotype.Component
import java.util.concurrent.CyclicBarrier

@Component
class FXSimilarImageHandler(
    private val fxmlLoader: EntranceFXMLLoader
): SimilarImageHandler {
    
    override fun handle(entryImage: EntryImage, similarImages: SimilarImages): SimilarImageHandleResult {
        var result = SimilarImageHandleResult(false)
        val barrier = CyclicBarrier(2)
    
        Platform.runLater {
            fxmlLoader.loadWithStage<HandleSimilarImageController>("similar/handle-similar-image.fxml") { stage, controller ->
                stage.initModality(Modality.APPLICATION_MODAL)
                stage.title = "類似画像"
                
                controller.init(stage, entryImage, similarImages)
    
                stage.showAndWait()
                result = controller.result

                barrier.await()
            }
        }

        barrier.await()
        
        return result
    }
}