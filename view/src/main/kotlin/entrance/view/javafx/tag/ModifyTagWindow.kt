package entrance.view.javafx.tag

import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class ModifyTagWindow (
    private val fxmlLoader: EntranceFXMLLoader,
    private val tagRepository: TagRepository
) {
    
    fun open(owner: Stage, tagName: TagName) {

        fxmlLoader.loadWithStage<ModifyTagController>("tag/modify-tag.fxml") { stage, controller ->
            stage.initModality(Modality.WINDOW_MODAL)
            stage.title = "タグ編集"
            stage.initOwner(owner)

            controller.stage = stage

            val tag = tagRepository.find(tagName)
            if (tag != null) {
                controller.setTag(tag)
                stage.showAndWait()
            } else {
                Dialog.warn(message="タグが存在しません")
            }
        }
    }
}