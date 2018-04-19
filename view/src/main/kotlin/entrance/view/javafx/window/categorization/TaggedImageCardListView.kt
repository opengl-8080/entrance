package entrance.view.javafx.window.categorization

import entrance.domain.categorization.TaggedImage
import javafx.scene.layout.Pane

class TaggedImageCardListView (
    private val pane: Pane
) {
    private lateinit var cardList: List<TaggedImageCard>
    
    val selectedImages: List<TaggedImage>
        get() = cardList.filter { it.selected }.map { it.taggedImage }

    val selected: Boolean
        get() = selectedImages.isNotEmpty()
    
    var onSelected: () -> Unit = {}
    
    fun add(images: List<TaggedImage>) {
        cardList = images.map { TaggedImageCard(it) }

        cardList.forEach {
            it.selectedProperty.addListener { _, _, _ ->
                onSelected()
            }
            pane.children += it
        }
    }
    
    fun clear() {
        pane.children.clear()
    }
    
    fun clearSelect() {
        cardList.forEach { it.selected = false }
    }
}