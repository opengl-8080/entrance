package entrance.infrastructure.entry

import entrance.domain.entry.EnteredImage
import entrance.domain.entry.EnteredImageRepository
import entrance.infrastructure.database.ImageTable
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.ItemTable
import entrance.infrastructure.database.ItemTableDao
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