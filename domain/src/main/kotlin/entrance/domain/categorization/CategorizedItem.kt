package entrance.domain.categorization

import entrance.domain.Rank
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.Tag

abstract class CategorizedItem (
    private val originalTagSet: Set<Tag>,
    private val originalRank: Rank
): Categorized {
    
    private var currentRank = originalRank
    override var rank: Rank
        get() = currentRank
        set(value) {
            currentRank = value
        }

//    private val originalTagSet = tagSet.toSet()
    private val currentTagSet = originalTagSet.toMutableSet()

    override fun forEachTag(iterator: (Tag) -> Unit) = currentTagSet.forEach(iterator)

    override fun isModified(): Boolean {
        val tagWasModified = originalTagSet != currentTagSet
        val rankWasModified = originalRank != currentRank
        return tagWasModified || rankWasModified
    }

    override fun add(tags: Set<Tag>) {
        currentTagSet.addAll(tags)
    }

    override fun remove(selectedTagSet: SelectedTagSet) {
        currentTagSet.removeAll(selectedTagSet.tags)
    }

    override val newAssignedTagSet: NewAssignedTagSet
        get() = NewAssignedTagSet(currentTagSet - originalTagSet)

    override val releasedTagSet: ReleasedTagSet
        get() = ReleasedTagSet(originalTagSet - currentTagSet)
}