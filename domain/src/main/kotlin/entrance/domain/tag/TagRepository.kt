package entrance.domain.tag

interface TagRepository {
    
    fun findAll(): AllTags
    
    fun register(tag: Tag)
}