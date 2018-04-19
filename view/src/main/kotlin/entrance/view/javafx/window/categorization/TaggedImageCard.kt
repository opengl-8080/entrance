package entrance.view.javafx.window.categorization

import entrance.domain.categorization.TaggedImage
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane

class TaggedImageCard(val taggedImage: TaggedImage) : GridPane() {
    private val selectedCssClass = "tagged-image-card__thumbnail--selected"
    
    @FXML
    lateinit var imageBorderPane: BorderPane
    @FXML
    lateinit var imageView: ImageView
    @FXML
    lateinit var tagsFlowPane: FlowPane
    
    var selectedProperty = SimpleBooleanProperty(false)
    var selected: Boolean
        get() = selectedProperty.value
        set(value) { selectedProperty.value = value }
    
    init {
        val loader = FXMLLoader(this.javaClass.getResource("/fxml/categorization/tagged-image-card.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        
        loader.load<Any>()
        
        imageView.image = Image(taggedImage.thumbnailUri.toString())
        imageView.fitWidthProperty().bind(imageBorderPane.widthProperty())
        imageView.fitHeightProperty().bind(imageBorderPane.heightProperty())
        
        taggedImage.tagSet.map { 
            Label(it.name.value).apply { this.styleClass += "tagged-image-card__tag" }
        }.forEach {
            tagsFlowPane.children += it
        }

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
    }
}