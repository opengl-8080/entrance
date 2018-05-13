package entrance.domain.sort

import entrance.domain.Rank
import entrance.domain.tag.Tag
import entrance.domain.tag.category.TagCategory


interface SortableItem {
    fun getTagsByCategory(tagCategory: TagCategory): List<Tag>
    val rank: Rank
}