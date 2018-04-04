package entrance.infrastructure.categorization

import entrance.domain.ItemId
import entrance.domain.categorization.NotTaggedImage
import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagId
import entrance.domain.tag.TagName
import entrance.infrastructure.database.*
import org.springframework.stereotype.Component

@Component
class RdbTaggedImageRepository(
    private val imageTableDao: ImageTableDao,
    private val itemTableDao: ItemTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val tagTableDao: TagTableDao
): TaggedImageRepository {
    override fun findNotTaggedImages(): List<NotTaggedImage> {
        return imageTableDao.findNotTaggedImages()
                .map { imageItemView ->
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    NotTaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile)
                }
    }

    override fun findTaggedImages(tagList: List<Tag>): List<TaggedImage> {
        return imageTableDao.findTaggedImagesByTagIdList(tagList.map { it.id.value })
                .map { imageItemView -> 
                    val relationalTagList = tagTableDao.findByItemId(imageItemView.id).map { tagTable ->
                        val tagId = TagId(tagTable.id)
                        val tagName = TagName(tagTable.name)
                        val tagFilterWord = TagFilterWord(tagTable.filterWord)
                        Tag(id = tagId, name = tagName, filterWord = tagFilterWord)
                    }
                    
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    TaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagList = relationalTagList)
                }
    }

    override fun save(taggedImage: TaggedImage) {
        val itemTable = itemTableDao.find(taggedImage.itemId.value)
        itemTableDao.update(itemTable)

        val itemTagTable = ItemTagTable()
        itemTagTable.itemId = itemTable.id
        
        itemTagTableDao.insert(itemTagTable)
    }
}