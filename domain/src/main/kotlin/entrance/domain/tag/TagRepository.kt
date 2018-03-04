package entrance.domain.tag

interface TagRepository {
    
    fun find(name: TagName): Tag?
    
    fun findAll(): AllTags
    
    fun register(tag: Tag)
}