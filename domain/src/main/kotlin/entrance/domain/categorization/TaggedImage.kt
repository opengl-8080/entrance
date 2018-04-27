package entrance.domain.categorization

import entrance.domain.BaseImageFile
import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.tag.Tag
import entrance.domain.util.file.LocalFile

class TaggedImage(
    val itemId: ItemId,
    localFile: LocalFile,
    tagSet: Set<Tag>,
    rank: Rank
): BaseImageFile(localFile) {

    private val originalRank = rank
    private var currentRank = rank
    var rank: Rank
        get() = currentRank
        set(value) {
            currentRank = value
        }
    
    private val originalTagSet = tagSet.toSet()
    private val currentTagSet = tagSet.toMutableSet()
    val tagSet: Set<Tag>
        get() = currentTagSet
    
    fun isModified(): Boolean {
        val tagWasModified = originalTagSet != currentTagSet
        val rankWasModified = originalRank != currentRank
        return tagWasModified || rankWasModified
    }
    
    fun add(tags: Set<Tag>) {
        currentTagSet.addAll(tags)
    }
    
    fun remove(tags: Set<Tag>) {
        currentTagSet.removeAll(tags)
    }
    
    val newAssignedTagSet: NewAssignedTagSet
        get() = NewAssignedTagSet(currentTagSet - originalTagSet)
    
    val releasedTagSet: ReleasedTagSet
        get() = ReleasedTagSet(originalTagSet - currentTagSet)
}
