package entrance.view.javafx.window.categorization

import entrance.application.categorization.CategorizeImageService
import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagSelectionView
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.stage.Stage

@FXPrototypeController
class CategorizeTagController (
    private val tagRepository: TagRepository,
    private val categorizeImageService: CategorizeImageService
) {
    private lateinit var stage: Stage
    private lateinit var onSavedListener: () -> Unit
    private lateinit var imageUnit: CategorizationImageUnit
    
    @FXML
    lateinit var tagFilterTextField: TextField
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var selectedTagsListView: ListView<Tag>
    
    lateinit var tagSelectionView: TagSelectionView
    
    fun init(stage: Stage, imageUnit: CategorizationImageUnit, onSavedListener: () -> Unit) {
        this.onSavedListener = onSavedListener
        this.stage = stage
        this.imageUnit = imageUnit
        
        val commonAssignedTags = imageUnit.commonAssignedTags
        tagSelectionView = TagSelectionView(tagRepository, tagsFlowPane, tagFilterTextField, selectedTagsListView)
        tagSelectionView.selectAll(commonAssignedTags.tagSet)
    }
    
    @FXML
    fun save() {
        categorizeImageService.categorize(imageUnit, tagSelectionView.selectedTagList.toSet())
        stage.close()
        onSavedListener.invoke()
    }
    
    @FXML
    fun reloadTags() {
        tagSelectionView.reload()
    }
    
    @FXML
    fun clearTagSelect() {
        tagSelectionView.clear()
    }
}