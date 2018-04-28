package entrance.infrastructure.tag.category

import entrance.domain.tag.category.*
import entrance.infrastructure.database.TagCategoryTable
import entrance.infrastructure.database.TagCategoryTableDao
import org.springframework.stereotype.Component

@Component
class RdbTagCategoryRepository(
    private val tagCategoryTableDao: TagCategoryTableDao
): TagCategoryRepository {
    override fun delete(tagCategory: TagCategory) {
        val tagCategoryTable = tagCategoryTableDao.find(tagCategory.id.value)
        tagCategoryTableDao.delete(tagCategoryTable)
    }

    override fun modify(modifiableTagCategory: ModifiableTagCategory) {
        val tagCategoryTable = TagCategoryTable()
        tagCategoryTable.id = modifiableTagCategory.id.value
        tagCategoryTable.name = modifiableTagCategory.name.value
        
        tagCategoryTableDao.update(tagCategoryTable)
    }

    override fun findForUpdate(name: TagCategoryName): ModifiableTagCategory? {
        val tagTable = tagCategoryTableDao.findByNameForUpdate(name.value)
        return tagTable?.let {
            val tagId = TagCategoryId(tagTable.id)
            val tagName = TagCategoryName(tagTable.name)
            ModifiableTagCategory(id = tagId, name = tagName)
        }
    }
    
    override fun find(name: TagCategoryName): TagCategory? {
        val tagCategoryTable = tagCategoryTableDao.findByName(name.value)
        return tagCategoryTable?.let { rebuild(it) }
    }

    override fun findAll(): AllTagCategories {
        val tagCategoryTables: MutableList<TagCategoryTable> = tagCategoryTableDao.findAll()
        val tags = tagCategoryTables.map(this::rebuild)
        
        return AllTagCategories(tags)
    }
    
    private fun rebuild(tagCategoryTable: TagCategoryTable): TagCategory {
        val tagId = TagCategoryId(tagCategoryTable.id)
        val tagName = TagCategoryName(tagCategoryTable.name)
        return TagCategory(id = tagId, name = tagName)
    }
    
    override fun register(newTagCategory: NewTagCategory) {
        val tagCategoryTable = TagCategoryTable()
        tagCategoryTable.name = newTagCategory.name.value
        tagCategoryTableDao.insert(tagCategoryTable)
        
        newTagCategory.id = TagCategoryId(tagCategoryTable.id)
    }
}