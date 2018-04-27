package entrance.infrastructure.categorization

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.entry.LibraryDirectory
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagId
import entrance.domain.tag.TagName
import entrance.domain.util.file.RelativePath
import entrance.infrastructure.database.*
import org.springframework.stereotype.Component

@Component
class RdbTaggedImageRepository(
    private val imageTableDao: ImageTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val tagTableDao: TagTableDao,
    private val itemTableDao: ItemTableDao
): TaggedImageRepository {
    override fun findNotTaggedImages(): List<TaggedImage> {
        return imageTableDao.findNotTaggedImages()
                .map { imageItemView ->
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    TaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = emptySet(), rank= Rank(imageItemView.rank))
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
                    TaggedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = relationalTagSet, rank= Rank(imageItemView.rank))
                }
    }
    
    override fun save(taggedImage: TaggedImage) {
        val itemId = taggedImage.itemId.value

        taggedImage.newAssignedTagSet.tagSet.forEach {  newAssignedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = newAssignedTag.id.value
            itemTagTableDao.insert(itemTagTable)
        }

        taggedImage.releasedTagSet.tagSet.forEach { releasedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = releasedTag.id.value
            itemTagTableDao.delete(itemTagTable)
        }

        val itemTable = itemTableDao.find(itemId)
        itemTable.rank = taggedImage.rank.value
        itemTableDao.update(itemTable)
    }
}