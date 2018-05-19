package entrance.view.javafx.window.similar

import entrance.domain.entry.entrance.EntryImage
import entrance.domain.entry.similar.SimilarImageHandleResult
import entrance.domain.entry.similar.SimilarImages
import entrance.view.javafx.util.Dialog
import entrance.view.javafx.util.FXPrototypeController
import javafx.beans.binding.StringBinding
import javafx.beans.property.SimpleIntegerProperty
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

@FXPrototypeController
class HandleSimilarImageController {
    lateinit var entryImage: EntryImage
    lateinit var similarImages: SimilarImages
    lateinit var stage: Stage
    
    @FXML
    lateinit var entryImageBorderPane: BorderPane
    @FXML
    lateinit var entryImageView: ImageView
    @FXML
    lateinit var entryImageSizeLabel: Label
    @FXML
    lateinit var similarImageBorderPane: BorderPane
    @FXML
    lateinit var similarImageView: ImageView
    @FXML
    lateinit var similarImageSizeLabel: Label
    @FXML
    lateinit var similarImagePageNumberLabel: Label
    
    private var similarImageIndex = SimpleIntegerProperty(0)
    private var _result: SimilarImageHandleResult = SimilarImageHandleResult.noSave()

    fun init(stage: Stage, entryImage: EntryImage, similarImages: SimilarImages) {
        this.stage = stage
        this.entryImage = entryImage
        this.similarImages = similarImages
        
        entryImageView.image = Image(entryImage.uri.toString())
        entryImageSizeLabel.text = "${entryImageView.image.width.toInt()} × ${entryImageView.image.height.toInt()}"

        loadSimilarImage()
        
        entryImageView.fitWidthProperty().bind(entryImageBorderPane.widthProperty())
        entryImageView.fitHeightProperty().bind(entryImageBorderPane.heightProperty())

        similarImageView.fitWidthProperty().bind(similarImageBorderPane.widthProperty())
        similarImageView.fitHeightProperty().bind(similarImageBorderPane.heightProperty())

        similarImagePageNumberLabel.textProperty().bind(object: StringBinding() {
            init {
                bind(similarImageIndex)
            }
            override fun computeValue(): String {
                return "(${similarImageIndex.value + 1}/${similarImages.size})"
            }
        })
    }
    
    @FXML
    fun delete() {
        if (!Dialog.confirm("エントリ画像を削除します.\nよろしいですか？")) {
            return
        }

        entryImage.delete()
        _result = SimilarImageHandleResult.noSave()
        stage.close()
    }
    
    @FXML
    fun save() {
        if (!Dialog.confirm("エントリ画像を保存します.\nよろしいですか？")) {
            return
        }

        _result = SimilarImageHandleResult.save()
        stage.close()
    }
    
    @FXML
    fun scrollSimilarImage(e: ScrollEvent) {
        var index = similarImageIndex.value
        
        if (0 < e.deltaY) {
            index++
        } else {
            index--
        }
        
        if (index < 0) {
            index = similarImages.size - 1
        } else if (similarImages.size <= index) {
            index = 0
        }

        similarImageIndex.value = index
        loadSimilarImage()
    }
    
    private fun loadSimilarImage() {
        val similarImage = similarImages[similarImageIndex.value]
        similarImageView.image = Image(similarImage.uri.toString())
        similarImageSizeLabel.text = "${similarImageView.image.width.toInt()} × ${similarImageView.image.height.toInt()}"
    }
    
    val result: SimilarImageHandleResult
        get() = _result
}