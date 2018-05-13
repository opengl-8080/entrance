package entrance.infrastructure.categorization.image

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.domain.categorization.image.CategorizedImage
import entrance.domain.categorization.image.CategorizedImageRepository
import entrance.domain.entry.LibraryDirectory
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.TagRepository
import entrance.domain.util.file.RelativePath
import entrance.infrastructure.database.image.ImageTableDao
import entrance.infrastructure.database.item.ItemTableDao
import entrance.infrastructure.database.tag.ItemTagTable
import entrance.infrastructure.database.tag.ItemTagTableDao
import entrance.infrastructure.database.tag.TagTableDao
import entrance.infrastructure.database.tag.category.TagCategoryTableDao
import org.springframework.stereotype.Component

@Component
class RdbCategorizedImageRepository(
    private val imageTableDao: ImageTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val itemTableDao: ItemTableDao,
    private val tagRepository: TagRepository
): CategorizedImageRepository {
    override fun findNotTaggedImages(rankCondition: RankCondition): List<CategorizedImage> {
        return imageTableDao.findNotTaggedImages(rankCondition.min.value, rankCondition.max.value)
                .map { imageItemView ->
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    CategorizedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = emptySet(), rank = Rank.of(imageItemView.rank))
                }
    }

    override fun findTaggedImages(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<CategorizedImage> {
        return imageTableDao.findTaggedImagesByTagIdList(selectedTagSet.idList, rankCondition.min.value, rankCondition.max.value)
                .map { imageItemView ->
                    val relationalTagSet = tagRepository.findByItemId(ItemId(imageItemView.id))
                    val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
                    CategorizedImage(itemId = ItemId(imageItemView.id), localFile = localFile, tagSet = relationalTagSet.toSet(), rank = Rank.of(imageItemView.rank))
                }
    }
    
    override fun save(categorizedImage: CategorizedImage) {
        val itemId = categorizedImage.itemId.value

        categorizedImage.newAssignedTagSet.tagSet.forEach { newAssignedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = newAssignedTag.id.value
            itemTagTableDao.insert(itemTagTable)
        }

        categorizedImage.releasedTagSet.tagSet.forEach { releasedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = releasedTag.id.value
            itemTagTableDao.delete(itemTagTable)
        }

        val itemTable = itemTableDao.find(itemId)
        itemTable.rank = categorizedImage.rank.value
        itemTableDao.update(itemTable)
    }
}