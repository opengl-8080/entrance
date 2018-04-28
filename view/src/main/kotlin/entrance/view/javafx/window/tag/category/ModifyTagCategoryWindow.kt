package entrance.view.javafx.window.tag

import entrance.domain.tag.category.TagCategoryName
import entrance.domain.tag.category.TagCategoryRepository
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class ModifyTagCategoryWindow(
    private val fxmlLoader: EntranceFXMLLoader,
    private val tagCategoryRepository: TagCategoryRepository
) {
    
    fun open(owner: Stage, tagCategoryName: TagCategoryName) {

        fxmlLoader.loadWithStage<ModifyTagCategoryController>("tag/category/modify-tag-category.fxml") { stage, controller ->
            stage.initModality(Modality.WINDOW_MODAL)
            stage.title = "タグカテゴリ編集"
            stage.initOwner(owner)

            controller.stage = stage

            val tagCategory = tagCategoryRepository.find(tagCategoryName)
            if (tagCategory != null) {
                controller.setTag(tagCategory)
                stage.showAndWait()
            } else {
                Dialog.warn(message="タグカテゴリが存在しません")
            }
        }
    }
}