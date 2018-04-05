package entrance.domain.categorization

import entrance.domain.tag.Tag

/**
 * 割り当てを解除されたタグのセット.
 */
data class ReleasedTagSet (
    val tagSet: Set<Tag>
)
