package entrance.domain.tag

class Tag private constructor(
    var id: TagId? = null,
    var name: TagName,
    private val filterWordList: MutableList<TagFilterWord> = mutableListOf()
) {
    
    companion object {
        fun new(name: TagName) = Tag(name = name)
    }
    
    
}
