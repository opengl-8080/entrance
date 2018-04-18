package entrance.view.javafx.window.categorization

import entrance.application.categorization.CategorizeImageService
import entrance.domain.categorization.CategorizationImageUnit
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.stage.Stage

@FXPrototypeController
class CategorizeTagController (
    private val categorizeImageService: CategorizeImageService
) {
    private lateinit var stage: Stage
    private lateinit var onSavedListener: () -> Unit
    private lateinit var imageUnit: CategorizationImageUnit
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    
    fun init(stage: Stage, imageUnit: CategorizationImageUnit, onSavedListener: () -> Unit) {
        this.onSavedListener = onSavedListener
        this.stage = stage
        this.imageUnit = imageUnit
        
        val commonAssignedTags = imageUnit.commonAssignedTags
        tagSelectController.selectAll(commonAssignedTags.tagSet)
    }
    
    @FXML
    fun save() {
        categorizeImageService.categorize(imageUnit, tagSelectController.selectedTagList.toSet())
        stage.close()
        onSavedListener.invoke()
    }
}