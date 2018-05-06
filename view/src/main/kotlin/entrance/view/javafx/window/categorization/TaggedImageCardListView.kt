package entrance.view.javafx.window.categorization

import entrance.domain.categorization.Categorized
import entrance.domain.tag.SelectedTagSet
import javafx.scene.layout.Pane

class TaggedImageCardListView (
    private val pane: Pane
) {
    private val cardList = mutableListOf<TaggedImageCard>()
    
    val selectedImages: List<Categorized>
        get() = cardList.filter { it.selected }.map { it.categorizedImage }

    val selected: Boolean
        get() = selectedImages.isNotEmpty()
    
    private val selectedCards: List<TaggedImageCard>
            get() = cardList.filter { it.selected }
    
    fun assign(selectedTagSet: SelectedTagSet) = selectedCards.forEach {it.assign(selectedTagSet)}
    fun release(selectedTagSet: SelectedTagSet) = selectedCards.forEach { it.release(selectedTagSet) }

    fun filterModifiedItems(): List<Categorized> = cardList.map { it.categorizedImage }.filter { it.isModified() }
    
    fun replaceAll(images: List<Categorized>) {
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