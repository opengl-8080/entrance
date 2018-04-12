package entrance.infrastructure.image

import entrance.domain.ItemId
import entrance.domain.entry.LibraryDirectory
import entrance.domain.util.file.RelativePath
import entrance.domain.viewer.StoredImage
import entrance.domain.viewer.StoredImageRepository
import entrance.domain.tag.Tag
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.ItemTableDao
import entrance.infrastructure.database.ItemTagTableDao
import org.springframework.stereotype.Component

@Component
class RdbStoredImageRepository(
    private val itemTableDao: ItemTableDao,
    private val imageTableDao: ImageTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory
): StoredImageRepository {

    override fun find(tagList: List<Tag>): List<StoredImage> {
        val categorizedTagList = imageTableDao.findTaggedImagesByTagIdList(tagList.map { it.id.value })
        return categorizedTagList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            StoredImage(ItemId(it.id), RelativePath(it.path), localFile)
        }
    }
    
    override fun findNotTaggedImage(): List<StoredImage> {
        val imageItemViewList = imageTableDao.findNotTaggedImages()
        return imageItemViewList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            StoredImage(ItemId(it.id), RelativePath(it.path), localFile)
        }
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