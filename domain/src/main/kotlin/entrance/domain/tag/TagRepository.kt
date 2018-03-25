package entrance.domain.tag

interface TagRepository {
    
    fun findForUpdate(name: TagName): ModifiableTag?
    
    fun find(name: TagName): Tag?
    
    fun findAll(): AllTags
    
    fun register(newTag: NewTag)
    
    fun modify(modifiableTag: ModifiableTag)
}