package entrance.domain.tag

import entrance.domain.ItemId
import entrance.domain.tag.category.TagCategory

interface TagRepository {
    
    fun findForUpdate(name: TagName): ModifiableTag?
    
    fun find(name: TagName): Tag?
    
    fun findAll(): AllTags
    
    fun register(newTag: NewTag)
    
    fun modify(modifiableTag: ModifiableTag)
    
    fun findByItemId(itemId: ItemId): List<Tag>

    fun findByTagCategory(tagCategory: TagCategory): List<Tag>
    
    fun findByTagCategoryForUpdate(tagCategory: TagCategory): List<ModifiableTag>
    
    fun delete(tag: Tag)
}
