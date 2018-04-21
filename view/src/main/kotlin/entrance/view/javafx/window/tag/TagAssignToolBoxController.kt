package entrance.view.javafx.window.tag

import entrance.domain.tag.Tag
import entrance.view.javafx.control.TagSelectController
import entrance.view.javafx.control.TagView
import entrance.view.javafx.util.FXPrototypeController
import javafx.fxml.FXML
import javafx.scene.layout.FlowPane

@FXPrototypeController
class TagAssignToolBoxController {
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var assignedTagHistoryPane: FlowPane
    
    private val tagHistoryViewSet = mutableSetOf<TagView>()
    
    var onAssign: (List<Tag>) -> Unit = {}
    
    @FXML
    fun assign() {
        val selectedTagList = tagSelectController.selectedTagList
        onAssign(selectedTagList)

        val newHistories = tagSelectController
                .selectedTagViewList
                .filter { originalTagView ->
                    tagHistoryViewSet.all { !it.hasSameTag(originalTagView.tag) }
                }
                .map { originalTagView ->
                    TagView(originalTagView.tag).apply {
                        selectedProperty().bindBidirectional(originalTagView.selectedProperty())
                    }
                }
        
        tagHistoryViewSet.addAll(newHistories)

        assignedTagHistoryPane.children.clear()
        tagHistoryViewSet.forEach { assignedTagHistoryPane.children += it }
    }
}