package entrance.domain.tag

import entrance.domain.tag.category.TagCategory

interface TagRepository {
    
    fun findForUpdate(name: TagName): ModifiableTag?
    
    fun find(name: TagName): Tag?
    
    fun findAll(): AllTags
    
    fun register(newTag: NewTag)
    
    fun modify(modifiableTag: ModifiableTag)
    
    fun findByTagCategoryForUpdate(tagCategory: TagCategory): List<ModifiableTag>
}
