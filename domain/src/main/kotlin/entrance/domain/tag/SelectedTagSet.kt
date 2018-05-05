package entrance.domain.tag

data class SelectedTagSet(val tags: Set<Tag>) {
    
    constructor(tag: Tag): this(setOf(tag))
    
    val idList: List<Long>
        get() = tags.map { it.id.value }
}
