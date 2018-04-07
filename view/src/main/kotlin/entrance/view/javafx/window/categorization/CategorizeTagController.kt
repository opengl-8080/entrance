package entrance.view.javafx.window.categorization

import entrance.application.categorization.CategorizeImageService
import entrance.domain.categorization.CategorizationImageUnit
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.view.javafx.control.TagView
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
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
    
    private val selectedTagSet = FXCollections.observableSet<Tag>()
    
    fun init(stage: Stage, imageUnit: CategorizationImageUnit, onSavedListener: () -> Unit) {
        this.onSavedListener = onSavedListener
        this.stage = stage
        this.imageUnit = imageUnit
        val commonAssignedTags = imageUnit.commonAssignedTags

        tagRepository.findAll().tags.forEach { tag ->
            val tagView = TagView(tag)
            tagsFlowPane.children += tagView
            
            if (commonAssignedTags.contains(tag)) {
                tagView.isSelected = true
                selectedTagSet += tag
            }

            tagView.selectedProperty().addListener { _, _, selected ->
                if (selected) {
                    selectedTagSet += tag
                } else {
                    selectedTagSet -= tag
                }
            }
        }
        
        tagFilterTextField.textProperty().addListener { _, _, text ->
            tagsFlowPane.children.map { child -> child as TagView }.forEach { tagView -> tagView.controlVisibility(text) }
        }
    }
    
    @FXML
    fun save() {
        categorizeImageService.categorize(imageUnit, selectedTagSet)
        stage.close()
        onSavedListener.invoke()
    }
}