package entrance.view.javafx.window.categorization

import entrance.application.categorization.CategorizeService
import entrance.domain.ItemType
import entrance.domain.Rank
import entrance.domain.categorization.book.CategorizedBook
import entrance.domain.categorization.book.CategorizedBookRepository
import entrance.domain.categorization.image.CategorizedImage
import entrance.domain.categorization.image.CategorizedImageRepository
import entrance.view.javafx.component.ItemTypeSelectController
import entrance.view.javafx.component.RankSelectController
import entrance.view.javafx.component.TagSelectController
import entrance.view.javafx.control.TagView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.FXPrototypeController
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.net.URL
import java.util.*

@FXPrototypeController
class CategorizationController (
    private val categorizedImageRepository: CategorizedImageRepository,
    private val categorizedBookRepository: CategorizedBookRepository,
    private val singleImageViewerWindow: SingleImageViewerWindow,
    private val categorizeService: CategorizeService
): Initializable {
    private lateinit var ownStage: Stage
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var itemTypeSelectController: ItemTypeSelectController
    @FXML
    lateinit var rankSelectController: RankSelectController
    @FXML
    lateinit var tagSelect2Controller: TagSelectController
    @FXML
    lateinit var assignedTagHistoryPane: FlowPane
    @FXML
    lateinit var imagesVBox: VBox
        
    lateinit var taggedImageCardListView: TaggedImageCardListView

    private val tagHistoryViewSet = mutableSetOf<TagView>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        taggedImageCardListView = TaggedImageCardListView(imagesVBox)

        tagSelectController.onReloaded = {
            taggedImageCardListView.clear()
        }

        rankSelectController.minRank = Rank.ONE

        tagSelect2Controller.onReloaded = {
            clearTagHistory()
        }
    }
    
    fun init(ownStage: Stage) {
        this.ownStage = ownStage
    }

    @FXML
    fun search() {
        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val images = if (tagSelectController.isNotSelected()) {
                    categorizedImageRepository.findNotTaggedImages(rankSelectController.condition)
                } else {
                    categorizedImageRepository.findTaggedImages(tagSelectController.selectedTagSet, rankSelectController.condition)
                }

                taggedImageCardListView.replaceAll(images)
            }
            
            ItemType.BOOK -> {
                val books = if (tagSelectController.isNotSelected()) {
                    categorizedBookRepository.findNotTaggedBooks(rankSelectController.condition)
                } else {
                    categorizedBookRepository.findTaggedBooks(tagSelectController.selectedTagSet, rankSelectController.condition)
                }

                taggedImageCardListView.replaceAll(books)
            }
        }
    }
    
    // メイン表示
    @FXML
    fun openViewer() {
        if (!taggedImageCardListView.selected) {
            return
        }
        
        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val selectedImages = taggedImageCardListView.selectedImages.map { it as CategorizedImage }
                val firstImage = selectedImages.first()
                singleImageViewerWindow.open(firstImage, selectedImages)
            }
            
            ItemType.BOOK -> {
                val selectedBooks = taggedImageCardListView.selectedImages.map { it as CategorizedBook }
                val topImages = selectedBooks.map { it.images.first() }
                singleImageViewerWindow.open(topImages.first(), topImages)
            }
        }
    }
    
    @FXML
    fun clearImageSelect() {
        taggedImageCardListView.clearSelect()
    }
    
    @FXML
    fun save() {
        if (!Dialog.confirm("タグの編集を保存します\nよろしいですか？")) {
            return
        }

        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val modifiedImages = taggedImageCardListView.filterModifiedItems().map { it as CategorizedImage }
                categorizeService.categorizeImages(modifiedImages)
            }
            
            ItemType.BOOK -> {
                val modifiedBooks = taggedImageCardListView.filterModifiedItems().map { it as CategorizedBook }
                categorizeService.categorizeBooks(modifiedBooks)
            }
        }
        
        search()
    }
    
    // タグ割り当て
    @FXML
    fun assignTag() {
        val selectedTagSet = tagSelect2Controller.selectedTagSet
        taggedImageCardListView.assign(selectedTagSet)
        saveHistory()
        tagSelect2Controller.clearTagSelect()
    }

    @FXML
    fun releaseTag() {
        val selectedTagSet = tagSelect2Controller.selectedTagSet
        taggedImageCardListView.release(selectedTagSet)
        saveHistory()
        tagSelect2Controller.clearTagSelect()
    }

    private fun saveHistory() {
        val newHistories = tagSelect2Controller
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

    @FXML
    fun clearTagHistory() {
        assignedTagHistoryPane.children.clear()
        tagHistoryViewSet.clear()
    }
}
