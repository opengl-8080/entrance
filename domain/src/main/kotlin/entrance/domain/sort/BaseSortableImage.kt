package entrance.domain.sort

import entrance.domain.Rank
import entrance.domain.tag.Tag
import entrance.domain.tag.category.TagCategory


class BaseSortableImage(
    private val tags: List<Tag>,
    override val rank: Rank
): SortableItem {
    
    override fun getTagsByCategory(tagCategory: TagCategory): List<Tag> {
        return tags.filter { it.tagCategory.id == tagCategory.id }.sortedBy { it.name }
    }
}