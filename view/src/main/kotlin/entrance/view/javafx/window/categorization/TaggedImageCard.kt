package entrance.view.javafx.window.categorization

import entrance.domain.Rank
import entrance.domain.categorization.Categorized
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.Tag
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane

class TaggedImageCard(val categorizedImage: Categorized) : GridPane()  {
    private val selectedCssClass = "tagged-image-card__thumbnail--selected"
    
    @FXML
    lateinit var imageBorderPane: BorderPane
    @FXML
    lateinit var imageView: ImageView
    @FXML
    lateinit var tagsFlowPane: FlowPane
    @FXML
    lateinit var rankGroup: ToggleGroup
    @FXML
    lateinit var rankRadioButton1: RadioButton
    @FXML
    lateinit var rankRadioButton2: RadioButton
    @FXML
    lateinit var rankRadioButton3: RadioButton
    @FXML
    lateinit var rankRadioButton4: RadioButton
    @FXML
    lateinit var rankRadioButton5: RadioButton
    
    private var selectedProperty = SimpleBooleanProperty(false)
    var selected: Boolean
        get() = selectedProperty.value
        set(value) { selectedProperty.value = value }
    
    init {
        val loader = FXMLLoader(this.javaClass.getResource("/fxml/categorization/tagged-image-card.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        
        loader.load<Any>()
        
        imageView.image = Image(categorizedImage.thumbnailUri.toString())
        imageView.fitWidthProperty().bind(imageBorderPane.widthProperty())
        imageView.fitHeightProperty().bind(imageBorderPane.heightProperty())

        renderTags()

        imageView.onMouseClicked = EventHandler { e ->
            if (e.button != MouseButton.PRIMARY) {
                return@EventHandler
            }

            selected = !selected
        }

        selectedProperty.addListener { _, _, _ ->
            if (selected) {
                imageView.styleClass += selectedCssClass
            } else {
                imageView.styleClass -= selectedCssClass
            }
        }

        rankRadioButton1.userData = 1
        rankRadioButton2.userData = 2
        rankRadioButton3.userData = 3
        rankRadioButton4.userData = 4
        rankRadioButton5.userData = 5
        
        rankGroup.selectToggle(when (categorizedImage.rank) {
            Rank.ONE -> rankRadioButton1
            Rank.TWO -> rankRadioButton2
            Rank.THREE -> rankRadioButton3
            Rank.FOUR -> rankRadioButton4
            Rank.FIVE -> rankRadioButton5
        })

        rankGroup.selectedToggleProperty().addListener { _, _, rankRadioButton ->
            if (rankRadioButton == null) {
                return@addListener
            }

            categorizedImage.rank = Rank.of(rankRadioButton.userData as Int)
        }
    }
    
    fun assign(selectedTagSet: SelectedTagSet) {
        categorizedImage.add(selectedTagSet.tags)
        renderTags()
    }
    
    fun release(selectedTagSet: SelectedTagSet) {
        categorizedImage.remove(selectedTagSet)
        renderTags()
    }
    
    private fun renderTags() {
        tagsFlowPane.children.clear()

        categorizedImage.forEachTag {
            val label = mapToTagLabel(it)
            addToFlowPane(label)
        }
    }
    
    private fun mapToTagLabel(tag: Tag): Label {
        val label = Label(tag.name.value)
        label.styleClass += "tagged-image-card__tag"
        return label
    }
    
    private fun addToFlowPane(label: Label) {
        tagsFlowPane.children += label
    }
}