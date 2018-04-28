package entrance.domain.tag

import entrance.domain.tag.category.TagCategory

class NewTag private constructor(
    var id: TagId? = null,
    val name: TagName,
    val filterWord: TagFilterWord,
    val tagCategory: TagCategory
) {
    
    constructor(name: TagName, filterWord: TagFilterWord, tagCategory: TagCategory):
            this(id=null, name=name, filterWord =filterWord, tagCategory = tagCategory)
}

