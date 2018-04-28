package entrance.domain.tag

import entrance.domain.tag.category.TagCategory

data class ModifiableTag (
    val id: TagId,
    var name: TagName,
    var filterWord: TagFilterWord,
    var tagCategory: TagCategory
)