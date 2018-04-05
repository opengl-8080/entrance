package entrance.infrastructure.categorization

import entrance.domain.ItemId
import entrance.domain.categorization.NewAssignedTagSet
import entrance.domain.categorization.ReleasedTagSet
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
    override fun findNotTaggedImages(): List<TaggedImage> {
        return imageTableDao.findNotTaggedImages()
                .map { imageItemView ->
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    TaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = emptySet())
                }
    }

    override fun findTaggedImages(tagList: List<Tag>): List<TaggedImage> {
        return imageTableDao.findTaggedImagesByTagIdList(tagList.map { it.id.value })
                .map { imageItemView -> 
                    val relationalTagSet = tagTableDao.findByItemId(imageItemView.id).map { tagTable ->
                        val tagId = TagId(tagTable.id)
                        val tagName = TagName(tagTable.name)
                        val tagFilterWord = TagFilterWord(tagTable.filterWord)
                        Tag(id = tagId, name = tagName, filterWord = tagFilterWord)
                    }.toSet()
                    
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    TaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = relationalTagSet)
                }
    }

    override fun save(taggedImage: TaggedImage, newAssignedTagSet: NewAssignedTagSet, releasedTagSet: ReleasedTagSet) {
        val itemId = taggedImage.itemId.value
        
        newAssignedTagSet.tagSet.forEach { newAssignedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = newAssignedTag.id.value
            itemTagTableDao.insert(itemTagTable)
        }
        
        releasedTagSet.tagSet.forEach { releasedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = releasedTag.id.value
            itemTagTableDao.delete(itemTagTable)
        }
    }
}