package entrance.infrastructure.categorization

import entrance.domain.categorization.CategorizedImage
import entrance.domain.categorization.CategorizedImageRepository
import entrance.infrastructure.database.ItemTableDao
import entrance.infrastructure.database.ItemTagTable
import entrance.infrastructure.database.ItemTagTableDao
import org.springframework.stereotype.Component

@Component
class RdbCategorizedImageRepository(
    private val itemTableDao: ItemTableDao,
    private val itemTagTableDao: ItemTagTableDao
): CategorizedImageRepository {
    
    override fun save(categorizedImage: CategorizedImage) {
        val itemTable = itemTableDao.find(categorizedImage.itemId.value)
        itemTable.notCategorized = false
        itemTableDao.update(itemTable)

        val itemTagTable = ItemTagTable()
        itemTagTable.itemId = itemTable.id
        itemTagTable.tagId = categorizedImage.mainTag.id.value
        itemTagTable.main = true
        
        itemTagTableDao.insert(itemTagTable)
    }
}