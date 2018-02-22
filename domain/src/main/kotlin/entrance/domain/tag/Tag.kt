package entrance.domain.tag

class Tag private constructor(
    var id: TagId? = null,
    var name: TagName,
    val tagFilterWord: TagFilterWord
) {
    
    companion object {
        fun new(name: TagName, tagFilterWord: TagFilterWord) = Tag(name = name, tagFilterWord = tagFilterWord)
        fun rebuild(id: TagId, name: TagName, tagFilterWord: TagFilterWord)
            = Tag(id, name, tagFilterWord)
    }
    
    
}
