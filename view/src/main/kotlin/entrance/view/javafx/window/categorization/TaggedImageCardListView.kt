package entrance.view.javafx.window.categorization

import entrance.domain.tag.SelectedTagSet
import entrance.domain.categorization.TaggedImage
import javafx.scene.layout.Pane

class TaggedImageCardListView (
    private val pane: Pane
) {
    private val cardList = mutableListOf<TaggedImageCard>()
    
    val selectedImages: List<TaggedImage>
        get() = cardList.filter { it.selected }.map { it.taggedImage }

    val selected: Boolean
        get() = selectedImages.isNotEmpty()
    
    private val selectedCards: List<TaggedImageCard>
            get() = cardList.filter { it.selected }
    
    fun assign(selectedTagSet: SelectedTagSet) = selectedCards.forEach {it.assign(selectedTagSet)}
    fun release(selectedTagSet: SelectedTagSet) = selectedCards.forEach { it.release(selectedTagSet) }

    fun filterModifiedImages(): List<TaggedImage> = cardList.map { it.taggedImage }.filter { it.isModified() }
    
    fun replaceAll(images: List<TaggedImage>) {
        clear()
        cardList.addAll(images.map { TaggedImageCard(it) })

        cardList.forEach {
            pane.children += it
        }
    }
    
    fun clear() {
        pane.children.clear()
        cardList.clear()
    }
    
    fun clearSelect() {
        cardList.forEach { it.selected = false }
    }
}