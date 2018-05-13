package entrance.domain.sort

import entrance.domain.tag.category.TagCategory

class TagCategorySortComparator(
    private val first: TagCategory?,
    private val second: TagCategory?,
    private val third: TagCategory?
): Comparator<SortableItem> {
    
    override fun compare(o1: SortableItem, o2: SortableItem): Int {
        var result = compare(o1, o2, first)
        if (result != 0) {
            return result
        }
        
        result = compare(o1, o2, second)
        if (result != 0) {
            return result
        }

        result = compare(o1, o2, third)
        if (result != 0) {
            return result
        }
        
        return -1 * o1.rank.compareTo(o2.rank)
    }
    
    private fun compare(o1: SortableItem, o2: SortableItem, tagCategory: TagCategory?): Int {
        if (tagCategory == null) {
            return 0
        }

        val o1Tags = o1.getTagsByCategory(tagCategory)
        val o2Tags = o2.getTagsByCategory(tagCategory)

        if (o1Tags.isEmpty() || o2Tags.isEmpty()) {
            return if (o1Tags.isEmpty() && o2Tags.isEmpty()) {
                0
            } else if (o1Tags.isNotEmpty()) {
                -1
            } else {
                1
            }
        }
        
        var result = 0

        o1Tags.forEachIndexed { index, o1Tag ->
            if (index < o2Tags.size) {
                result = o1Tag.name.compareTo(o2Tags[index].name)
                if (result != 0) {
                    return result
                }
            } else {
                return@forEachIndexed
            }
        }

        return result
    }
}
