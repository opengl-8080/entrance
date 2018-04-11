package entrance.infrastructure.image

import entrance.domain.ItemId
import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.domain.image.Image
import entrance.domain.image.ImageRepository
import entrance.domain.tag.Tag
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.ItemTableDao
import entrance.infrastructure.database.ItemTagTableDao
import org.springframework.stereotype.Component

@Component
class RdbImageRepository(
    private val itemTableDao: ItemTableDao,
    private val imageTableDao: ImageTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory
): ImageRepository {

    override fun find(tagList: List<Tag>): List<Image> {
        val categorizedTagList = imageTableDao.findTaggedImagesByTagIdList(tagList.map { it.id.value })
        return categorizedTagList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            Image(ItemId(it.id), RelativePath(it.path), localFile)
        }
    }
    
    override fun findNotTaggedImage(): List<Image> {
        val imageItemViewList = imageTableDao.findNotTaggedImages()
        return imageItemViewList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            Image(ItemId(it.id), RelativePath(it.path), localFile)
        }
    }
    
    override fun delete(image: Image) {
        val itemTagTableList = itemTagTableDao.findByItemId(image.itemId.value)
        
        itemTagTableList.forEach { itemTagTableDao.delete(it) }

        val imageTable = imageTableDao.find(image.itemId.value)
        imageTableDao.delete(imageTable)

        val itemTable = itemTableDao.find(image.itemId.value)
        itemTableDao.delete(itemTable)
    }
}