package entrance.domain.categorization

import entrance.domain.BaseImageFile
import entrance.domain.ItemId
import entrance.domain.tag.Tag
import entrance.domain.util.file.LocalFile

class TaggedImage(
    val itemId: ItemId,
    localFile: LocalFile,
    tagSet: Set<Tag>
): BaseImageFile(localFile) {

    private val originalTagSet = tagSet.toSet()
    private val currentTagSet = tagSet.toMutableSet()
    val tagSet: Set<Tag>
        get() = currentTagSet
    
    fun isModified(): Boolean = originalTagSet != currentTagSet
    
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
