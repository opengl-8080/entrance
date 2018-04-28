package entrance.view.javafx.window.tag.category

import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class TagCategoryMaintenanceWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {

    private var opened = false

    fun open(owner: Stage) {
        if (opened) {
            return
        }
        
        fxmlLoader.loadWithStage<TagCategoryMaintenanceController>("tag/category/tag-category-maintenance.fxml") { stage, controller ->
            stage.title = "タグカテゴリメンテナンス"
            stage.initOwner(owner)

            controller.stage = stage
            opened = true

            stage.showAndWait()

            opened = false
        }
    }
}