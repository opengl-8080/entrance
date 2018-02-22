package entrance.infrastructure.tag

import entrance.domain.tag.*
import entrance.infrastructure.database.TagTable
import entrance.infrastructure.database.TagTableDao
import org.springframework.stereotype.Component

@Component
class RdbTagRepository(
    private val tagTableDao: TagTableDao
): TagRepository {
    
    override fun findAll(): AllTags {
        val tagTables: MutableList<TagTable> = tagTableDao.findAll()
        val tags = tagTables.map(this::rebuild)
        
        return AllTags(tags)
    }
    
    private fun rebuild(tagTable: TagTable): Tag {
        val tagId = TagId(tagTable.id)
        val tagName = TagName(tagTable.name)
        val tagFilterWord = TagFilterWord(tagTable.filterWord)
        return Tag.rebuild(id = tagId, name = tagName, tagFilterWord = tagFilterWord)
    }
    
    override fun register(tag: Tag) {
        val tagTable = TagTable()
        tagTable.name = tag.name.value
        tagTable.filterWord = tag.tagFilterWord.value
        tagTableDao.insert(tagTable)
        
        tag.id = TagId(tagTable.id)
    }
}