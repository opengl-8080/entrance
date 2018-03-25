package entrance.domain.tag

class NewTag private constructor(
    var id: TagId? = null,
    var name: TagName,
    val filterWord: TagFilterWord
) {
    
    constructor(name: TagName, filterWord: TagFilterWord): this(id=null, name=name, filterWord =filterWord)
}

