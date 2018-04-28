package entrance.domain.tag.category

interface TagCategoryRepository {

    fun findForUpdate(name: TagCategoryName): ModifiableTagCategory?

    fun find(name: TagCategoryName): TagCategory?

    fun findAll(): AllTagCategories

    fun register(newTagCategory: NewTagCategory)

    fun modify(modifiableTagCategory: ModifiableTagCategory)
    
    fun delete(tagCategory: TagCategory)
}