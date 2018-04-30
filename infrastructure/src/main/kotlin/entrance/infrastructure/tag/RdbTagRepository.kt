package entrance.infrastructure.tag

import entrance.domain.tag.*
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryId
import entrance.domain.tag.category.TagCategoryName
import entrance.infrastructure.database.TagCategoryTableDao
import entrance.infrastructure.database.TagTable
import entrance.infrastructure.database.TagTableDao
import org.springframework.stereotype.Component

@Component
class RdbTagRepository(
    private val tagTableDao: TagTableDao,
    private val tagCategoryTableDao: TagCategoryTableDao
): TagRepository {
    override fun delete(tag: Tag) {
        val tagTable = tagTableDao.find(tag.id.value)
        tagTableDao.delete(tagTable)
    }

    override fun findByTagCategory(tagCategory: TagCategory): List<Tag> {
        val tagTables = tagTableDao.findByTagCategoryId(tagCategory.id.value)
        return tagTables.map { toTag(it) }
    }

    override fun findByTagCategoryForUpdate(tagCategory: TagCategory): List<ModifiableTag> {
        val tagTables = tagTableDao.findByTagCategoryIdForUpdate(tagCategory.id.value)
        return tagTables.map { toModifiableTag(it) }
    }

    override fun modify(modifiableTag: ModifiableTag) {
        val tagTable = TagTable()
        tagTable.id = modifiableTag.id.value
        tagTable.name = modifiableTag.name.value
        tagTable.filterWord = modifiableTag.filterWord.value
        tagTable.tagCategoryId = modifiableTag.tagCategory.id.value
        
        tagTableDao.update(tagTable)
    }

    override fun findForUpdate(name: TagName): ModifiableTag? {
        val tagTable = tagTableDao.findByNameForUpdate(name.value)
        return tagTable?.let { toModifiableTag(it) }
    }
    
    private fun toModifiableTag(tagTable: TagTable): ModifiableTag {
        val tagId = TagId(tagTable.id)
        val tagName = TagName(tagTable.name)
        val tagFilterWord = TagFilterWord(tagTable.filterWord)

        val tagCategory = toTagCategory(tagTable)
        
        return ModifiableTag(id = tagId, name = tagName, filterWord = tagFilterWord, tagCategory = tagCategory)
    }
    
    override fun find(name: TagName): Tag? {
        val tagTable = tagTableDao.findByName(name.value)
        return tagTable?.let { toTag(it) }
    }

    override fun findAll(): AllTags {
        val tagTables: MutableList<TagTable> = tagTableDao.findAll()
        val tags = tagTables.map(this::toTag)
        
        return AllTags(tags)
    }
    
    private fun toTag(tagTable: TagTable): Tag {
        val tagId = TagId(tagTable.id)
        val tagName = TagName(tagTable.name)
        val tagFilterWord = TagFilterWord(tagTable.filterWord)
        
        val tagCategory = toTagCategory(tagTable)
        
        return Tag(id = tagId, name = tagName, filterWord = tagFilterWord, tagCategory = tagCategory)
    }
    
    private fun toTagCategory(tagTable: TagTable): TagCategory {
        val tagCategoryTable = tagCategoryTableDao.find(tagTable.tagCategoryId)
        val tagCategoryId = TagCategoryId(tagCategoryTable.id)
        val tagCategoryName = TagCategoryName(tagCategoryTable.name)
        return TagCategory(id = tagCategoryId, name = tagCategoryName)
    }
    
    override fun register(newTag: NewTag) {
        val tagTable = TagTable()
        tagTable.name = newTag.name.value
        tagTable.filterWord = newTag.filterWord.value
        tagTable.tagCategoryId = newTag.tagCategory.id.value
        tagTableDao.insert(tagTable)
        
        newTag.id = TagId(tagTable.id)
    }
}