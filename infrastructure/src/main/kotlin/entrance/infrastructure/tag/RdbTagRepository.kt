package entrance.infrastructure.tag

import entrance.domain.tag.*
import entrance.infrastructure.database.TagTable
import entrance.infrastructure.database.TagTableDao
import org.springframework.stereotype.Component

@Component
class RdbTagRepository(
    private val tagTableDao: TagTableDao
): TagRepository {
    override fun modify(modifiableTag: ModifiableTag) {
        val tagTable = TagTable()
        tagTable.id = modifiableTag.id.value
        tagTable.name = modifiableTag.name.value
        tagTable.filterWord = modifiableTag.filterWord.value
        
        tagTableDao.update(tagTable)
    }

    override fun findForUpdate(name: TagName): ModifiableTag? {
        val tagTable = tagTableDao.findByNameForUpdate(name.value)
        return tagTable?.let {
            val tagId = TagId(tagTable.id)
            val tagName = TagName(tagTable.name)
            val tagFilterWord = TagFilterWord(tagTable.filterWord)
            ModifiableTag(id = tagId, name = tagName, filterWord = tagFilterWord)
        }
    }
    
    override fun find(name: TagName): Tag? {
        val tagTable = tagTableDao.findByName(name.value)
        return tagTable?.let { rebuild(it) }
    }

    override fun findAll(): AllTags {
        val tagTables: MutableList<TagTable> = tagTableDao.findAll()
        val tags = tagTables.map(this::rebuild)
        
        return AllTags(tags)
    }
    
    private fun rebuild(tagTable: TagTable): Tag {
        val tagId = TagId(tagTable.id)
        val tagName = TagName(tagTable.name)
        val tagFilterWord = TagFilterWord(tagTable.filterWord)
        return Tag(id = tagId, name = tagName, filterWord = tagFilterWord)
    }
    
    override fun register(newTag: NewTag) {
        val tagTable = TagTable()
        tagTable.name = newTag.name.value
        tagTable.filterWord = newTag.filterWord.value
        tagTableDao.insert(tagTable)
        
        newTag.id = TagId(tagTable.id)
    }
}