package entrance.domain.categorization

import entrance.domain.tag.Tag

/**
 * 新規に割り当てられたタグのセット.
 */
data class NewAssignedTagSet (
    val tagSet: Set<Tag>
)