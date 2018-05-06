package entrance.infrastructure.entry.image

import entrance.domain.entry.image.EnteredImage
import entrance.domain.entry.image.EnteredImageRepository
import entrance.infrastructure.database.image.ImageTable
import entrance.infrastructure.database.image.ImageTableDao
import entrance.infrastructure.database.item.ItemTable
import entrance.infrastructure.database.item.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbEnteredImageRepository (
        private val imageTableDao: ImageTableDao,
        private val itemTableDao: ItemTableDao
): EnteredImageRepository {
    
    override fun save(enteredImage: EnteredImage) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = enteredImage.registeredDateTime
        itemTable.rank = 1
        itemTableDao.insert(itemTable)

        val imageTable = ImageTable()
        imageTable.itemId = itemTable.id
        imageTable.path = enteredImage.relativePath.asString()
        imageTableDao.insert(imageTable)
    }
}