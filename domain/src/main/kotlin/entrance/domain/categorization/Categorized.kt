package entrance.domain.categorization

import entrance.domain.Rank
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.Tag
import java.net.URI

interface Categorized {
    var rank: Rank
    val newAssignedTagSet: NewAssignedTagSet
    val releasedTagSet: ReleasedTagSet
    val thumbnailUri: URI
    
    fun forEachTag(iterator: (Tag) -> Unit)
    fun isModified(): Boolean
    fun add(tags: Set<Tag>)
    fun remove(selectedTagSet: SelectedTagSet)
}
