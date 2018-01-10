package entrance.infrastructure.categorization

import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.infrastructure.database.ImageTable
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.ItemTable
import entrance.infrastructure.database.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbNotCategorizedImageRepository (
    private val itemTableDao: ItemTableDao,
    private val imageTableDao: ImageTableDao
): NotCategorizedImageRepository {
    
    override fun save(notCategorizedImage: NotCategorizedImage) {
        val itemTable = ItemTable()
        itemTable.notCategorized = true
        itemTable.registeredDateTime = notCategorizedImage.registeredDateTime.value
        itemTableDao.insert(itemTable)

        val imageTable = ImageTable()
        imageTable.itemId = itemTable.id
        imageTable.path = notCategorizedImage.stringPath()
        imageTableDao.insert(imageTable)
    }
}