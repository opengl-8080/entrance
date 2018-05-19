package entrance.infrastructure.viewer.image

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.domain.base.file.RelativePath
import entrance.domain.entry.library.LibraryDirectory
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.TagRepository
import entrance.domain.viewer.image.StoredImage
import entrance.domain.viewer.image.StoredImageRepository
import entrance.infrastructure.database.image.ImageTableDao
import entrance.infrastructure.database.item.ItemTableDao
import entrance.infrastructure.database.tag.ItemTagTableDao
import entrance.infrastructure.database.view.ImageItemView
import org.springframework.stereotype.Component

@Component
class RdbStoredImageRepository(
    private val itemTableDao: ItemTableDao,
    private val imageTableDao: ImageTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val tagRepository: TagRepository
): StoredImageRepository {

    override fun find(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<StoredImage> {
        val categorizedTagList =
                imageTableDao.findTaggedImagesByTagIdList(selectedTagSet.idList, rankCondition.min.value, rankCondition.max.value)
        return categorizedTagList.map { it -> toStoredImage(it) }
    }
    
    override fun findNotTaggedImage(rankCondition: RankCondition): List<StoredImage> {
        val imageItemViewList = imageTableDao.findNotTaggedImages(rankCondition.min.value, rankCondition.max.value)
        return imageItemViewList.map { it -> toStoredImage(it) }
    }
    
    private fun toStoredImage(imageItemView: ImageItemView): StoredImage {
        val tags = tagRepository.findByItemId(ItemId(imageItemView.id))
        val localFile = libraryDirectory.resolveFile(RelativePath(imageItemView.path))
        val rank = Rank.of(imageItemView.rank)
        return StoredImage(ItemId(imageItemView.id), RelativePath(imageItemView.path), localFile, tags, rank)
    }
    
    override fun delete(storedImage: StoredImage) {
        val itemTagTableList = itemTagTableDao.findByItemId(storedImage.itemId.value)
        
        itemTagTableList.forEach { itemTagTableDao.delete(it) }

        val imageTable = imageTableDao.find(storedImage.itemId.value)
        imageTableDao.delete(imageTable)

        val itemTable = itemTableDao.find(storedImage.itemId.value)
        itemTableDao.delete(itemTable)
    }
}