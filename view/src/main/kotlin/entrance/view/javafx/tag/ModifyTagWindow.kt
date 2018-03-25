package entrance.view.javafx.tag

import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class ModifyTagWindow (
    private val fxmlLoader: EntranceFXMLLoader,
    private val tagRepository: TagRepository
) {
    
    fun open(owner: Stage, tagName: TagName) {
        val fxmlContext = fxmlLoader.load<ModifyTagController>("tag/modify-tag.fxml")

        val stage = Stage()
        stage.initModality(Modality.WINDOW_MODAL)
        stage.scene = Scene(fxmlContext.root)
        stage.title = "タグ編集"
        stage.initOwner(owner)

        fxmlContext.controller.stage = stage

        val tag = tagRepository.find(tagName)
        if (tag != null) {
            fxmlContext.controller.setTag(tag)
            stage.showAndWait()
        } else {
            Dialog.warn(message="タグが存在しません")
        }
    }
}