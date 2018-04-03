package entrance.view.javafx.management

import entrance.domain.management.ManagedImage
import entrance.view.javafx.util.EntranceFXMLLoader
import javafx.scene.Scene
import javafx.stage.Modality
import javafx.stage.Stage
import org.springframework.stereotype.Component

@Component
class ManagementImageTagWindow (
    private val fxmlLoader: EntranceFXMLLoader
) {
    
    fun open(owner: Stage, imageList: List<ManagedImage>) {
        val context = fxmlLoader.load<ManagementImageTagController>("management/management-image-tag.fxml")
        context.controller.imageList = imageList
        
        val stage = Stage()
        stage.initOwner(owner)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.title = "タグ管理"
        stage.scene = Scene(context.root)
        
        stage.showAndWait()
    }
}