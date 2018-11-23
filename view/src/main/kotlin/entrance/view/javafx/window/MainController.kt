package entrance.view.javafx.window

import entrance.application.deletion.DeleteBookService
import entrance.application.deletion.DeleteImageService
import entrance.domain.ItemType
import entrance.domain.ThumbnailImage
import entrance.domain.viewer.book.StoredBook
import entrance.domain.viewer.book.StoredBookRepository
import entrance.domain.viewer.image.StoredImage
import entrance.domain.viewer.image.StoredImageRepository
import entrance.view.javafx.component.ItemTypeSelectController
import entrance.view.javafx.component.RankSelectController
import entrance.view.javafx.component.TagCategorySortController
import entrance.view.javafx.component.TagSelectController
import entrance.view.javafx.control.ThumbnailView
import entrance.view.javafx.control.ThumbnailsView
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.window.categorization.CategorizationWindow
import entrance.view.javafx.window.entry.EntryProgressWindow
import entrance.view.javafx.window.tag.TagMaintenanceWindow
import entrance.view.javafx.window.tag.category.TagCategoryMaintenanceWindow
import entrance.view.javafx.window.viewer.SingleImageViewerWindow
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import org.springframework.stereotype.Component
import java.net.URL
import java.util.*

@Component
class MainController(
    private val tagMaintenanceWindow: TagMaintenanceWindow,
    private val categorizationWindow: CategorizationWindow,
    private val storedImageRepository: StoredImageRepository,
    private val singleImageViewerWindow: SingleImageViewerWindow,
    private val deleteImageService: DeleteImageService,
    private val tagCategoryMaintenanceWindow: TagCategoryMaintenanceWindow,
    private val entryProgressWindow: EntryProgressWindow,
    private val storedBookRepository: StoredBookRepository,
    private val deleteBookService: DeleteBookService
) : Initializable {
    lateinit var primaryStage: Stage
    
    @FXML
    lateinit var tagSelectController: TagSelectController
    @FXML
    lateinit var itemTypeSelectController: ItemTypeSelectController
    @FXML
    lateinit var rankSelectController: RankSelectController
    @FXML
    lateinit var tagCategorySortController: TagCategorySortController
    
    @FXML
    lateinit var thumbnailsPane: FlowPane
    @FXML
    lateinit var openImageMenuItem: MenuItem
    @FXML
    lateinit var statusBarLabel: Label

    lateinit var thumbnailsView: ThumbnailsView<ThumbnailImage>
    private val images = mutableListOf<StoredImage>()
    private val books = mutableListOf<StoredBook>()
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        thumbnailsView = ThumbnailsView(thumbnailsPane)

        statusBarLabel.text = ""
        thumbnailsView.selectedThumbnailProperty.addListener { _, _, thumbnailImage -> 
            statusBarLabel.text = thumbnailImage?.statusText ?: ""
        }
    }
    
    @FXML
    fun search() {
        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val storedImages = if (tagSelectController.isNotSelected()) {
                    storedImageRepository.findNotTaggedImage(rankSelectController.condition)
                } else {
                    storedImageRepository.find(tagSelectController.selectedTagSet, rankSelectController.condition)
                }.sortedWith(tagCategorySortController.comparator)
                
                images.clear()
                images.addAll(storedImages)
                thumbnailsView.images = images
            }
            
            ItemType.BOOK -> {
                val storedBooks = if (tagSelectController.isNotSelected()) {
                    storedBookRepository.findNotTaggedBook(rankSelectController.condition)
                } else {
                    storedBookRepository.find(tagSelectController.selectedTagSet, rankSelectController.condition)
                }.sortedWith(tagCategorySortController.comparator)

                books.clear()
                books.addAll(storedBooks)
                thumbnailsView.images = books
            }
        }
    }
    
    // コンテキストメニュー
    @FXML
    fun openImage() {
        val selectedThumbnail: ThumbnailView<ThumbnailImage>? = thumbnailsView.selectedThumbnail
        
        if (selectedThumbnail == null) {
            return
        }

        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val firstImage = toStoredImage(selectedThumbnail)
                singleImageViewerWindow.open(firstImage, images)
            }
            
            ItemType.BOOK -> {
                val book = toSoredBook(selectedThumbnail)
                singleImageViewerWindow.open(book.images.first(), book.images)
            }
        }
    }
    
    @FXML
    fun deleteImage() {
        val selectedThumbnail: ThumbnailView<ThumbnailImage>? = thumbnailsView.selectedThumbnail

        if (selectedThumbnail == null) {
            return
        }

        when (itemTypeSelectController.itemType) {
            ItemType.IMAGE -> {
                val targetImage = toStoredImage(selectedThumbnail)

                if (Dialog.confirm("削除してもよろしいですか？")) {
                    deleteImageService.delete(targetImage)
                    search()
                }
            }
            
            ItemType.BOOK -> {
                val targetBook = toSoredBook(selectedThumbnail)

                if (Dialog.confirm("削除してもよろしいですか？")) {
                    deleteBookService.delete(targetBook)
                    search()
                }
            }
        }
    }
    
    private fun toSoredBook(thumbnailView: ThumbnailView<ThumbnailImage>): StoredBook {
        return books.first { it == thumbnailView.thumbnailImage }
    }

    private fun toStoredImage(thumbnailView: ThumbnailView<ThumbnailImage>): StoredImage {
        return images.first { it == thumbnailView.thumbnailImage }
    }
    
    // メニューバー
    @FXML
    fun openTagCategoryMaintenance() {
        tagCategoryMaintenanceWindow.open(primaryStage)
    }

    @FXML
    fun openTagMaintenance() {
        tagMaintenanceWindow.open(primaryStage)
    }

    @FXML
    fun openCategorizationWindow() {
        categorizationWindow.open(primaryStage)
    }
    
    @FXML
    fun executeEntry() {
        if (Dialog.confirm("画像の読み込みを開始します。\nよろしいですか？")) {
            entryProgressWindow.open(primaryStage)
        }
    }
}
