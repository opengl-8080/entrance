package entrance.view.javafx.categorization

import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.tag.Tag
import entrance.view.javafx.control.ThumbnailView
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class AssignedImageController {
    @FXML
    lateinit var root: Parent
    @FXML
    lateinit var tagNameLabel: Label
    @FXML
    lateinit var imagesPane: FlowPane
    
    private val thumbnailViewList = mutableListOf<ThumbnailView>()
    
    fun init(tag: Tag, notCategorizedImageList: List<NotCategorizedImage>) {
        tagNameLabel.text = tag.name.value
        
        addImage(notCategorizedImageList)
    }
    
    fun addImage(notCategorizedImageList: List<NotCategorizedImage>) {
        notCategorizedImageList.forEach { image ->
            val thumbnailView = ThumbnailView(image)
            thumbnailViewList += thumbnailView
            imagesPane.children += thumbnailView
        }
    }
    
    fun takeSelectedImageList(): List<NotCategorizedImage> {
        val selected = thumbnailViewList.filter { it.selected }
        
        thumbnailViewList.removeAll(selected)
        imagesPane.children.removeAll(selected)
        
        return selected.map { it.notCategorizedImage }
    }
    
    fun isEmpty() = thumbnailViewList.isEmpty()
}
