package entrance.view.javafx.window.similar

import entrance.domain.entry.image.EntryImage
import entrance.domain.similar.SimilarImage
import entrance.domain.similar.SimilarImageHandleResult
import entrance.domain.similar.SimilarImageHandler
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.application.Platform
import javafx.stage.Modality
import org.springframework.stereotype.Component
import java.util.concurrent.CyclicBarrier

@Component
class FXSimilarImageHandler(
    private val fxmlLoader: EntranceFXMLLoader
): SimilarImageHandler {
    
    override fun handle(entryImage: EntryImage, similarImages: List<SimilarImage>): SimilarImageHandleResult {
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