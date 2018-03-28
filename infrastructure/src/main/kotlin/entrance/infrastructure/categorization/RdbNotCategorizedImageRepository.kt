package entrance.infrastructure.categorization

import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbNotCategorizedImageRepository (
    private val itemTableDao: ItemTableDao,
    private val imageTableDao: ImageTableDao
): NotCategorizedImageRepository {
    
    override fun loadAll(): List<NotCategorizedImage> {
        return listOf()
    }
}
