package entrance.infrastructure.database

import entrance.domain.*
import org.springframework.stereotype.Component

@Component
class ImageRepositoryImpl (
    private val itemDao: ItemTableDao,
    private val imageDao: ImageTableDao,
    private val tagDao: TagTableDao,
    private val itemTagDao: ItemTagTableDao
): ImageRepository {
    
    override fun save(imageItem: ImageItem) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = imageItem.registeredDateTime.value
        itemTable.uncategorized = imageItem.uncategorized
        
        itemDao.save(itemTable)

        val imageTable = ImageTable()
        imageTable.itemId = itemTable.id
        imageTable.path = imageItem.file.stringPath()
        
        imageDao.save(imageTable)

        val relationalTags = imageItem.relationalTags
        val mainTag = relationalTags.mainTag
        if (mainTag != null) {
            saveItemTag(itemTable, mainTag, main = true)
        }

        relationalTags.tags.forEach { tag ->
            saveItemTag(itemTable, tag, main = false)
        }
    }
    
    private fun saveItemTag(itemTable: ItemTable, tag: Tag, main: Boolean) {
        val itemTagTable = ItemTagTable()
        itemTagTable.itemId = itemTable.id
        itemTagTable.tagId = tag.id.value
        itemTagTable.main = main
        itemTagDao.save(itemTagTable)
    }

    override fun find(id: ItemId): ImageItem {
        val itemTable = itemDao.find(id.value)
        val imageTable = imageDao.find(id.value)

        var mainTag: Tag? = null
        val tags = mutableListOf<Tag>()
        val itemTags = itemTagDao.findByItemId(id.value)
        itemTags.map { itemTag ->
            val tagTable = tagDao.find(itemTag.tagId)
            val tag = Tag(TagId(tagTable.id), TagName(tagTable.name))
            
            if (itemTag.main) {
                mainTag = tag
            } else {
                tags.add(tag)
            }
        }
        val relationalTags = RelationalTags(mainTag = mainTag, tags = tags)
        
        return ImageItem(
            itemId = ItemId(itemTable.id),
            uncategorized = itemTable.uncategorized,
            registeredDateTime = RegisteredDateTime(itemTable.registeredDateTime),
            relationalTags = relationalTags,
            file = ImageFile(RelativePath(imageTable.path))
        )
    }
}