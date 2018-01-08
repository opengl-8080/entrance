package entrance.infrastructure.database

import entrance.domain.ImageItem
import entrance.domain.ImageRepository
import entrance.domain.ItemId
import org.springframework.stereotype.Component

@Component
class ImageRepositoryImpl (
    private val itemDao: ItemTableDao,
    private val imageDao: ImageTableDao
): ImageRepository {
    
    override fun save(imageItem: ImageItem) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = imageItem.registeredDateTime.value
        itemTable.uncategorized = imageItem.uncategorized
        
        itemDao.save(itemTable)

        val imageTable = ImageTable()
        imageTable.itemId = itemTable.id
        imageTable.path = imageItem.file.stringPath()
    }

    override fun find(id: ItemId): ImageItem {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}