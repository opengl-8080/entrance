package entrance.domain.tag.category


class NewTagCategory private constructor(
    var id: TagCategoryId? = null,
    var name: TagCategoryName
) {

    constructor(name: TagCategoryName): this(id=null, name=name)
}

