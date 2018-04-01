package entrance.infrastructure.categorization

import entrance.domain.ItemId
import entrance.domain.RegisteredDateTime
import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.categorization.NotCategorizedImageRepository
import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.infrastructure.database.ImageTableDao
import org.springframework.stereotype.Component

@Component
class RdbNotCategorizedImageRepository (
    private val imageTableDao: ImageTableDao,
    private val libraryDirectory: LibraryDirectory
): NotCategorizedImageRepository {
    
    override fun loadAll(): List<NotCategorizedImage> {
        val notCategorizedImages = imageTableDao.findNotCategorizedImages()
        return notCategorizedImages.map {
            val relativePath = RelativePath(it.path)
            val localFile = libraryDirectory.resolveFile(relativePath)
            NotCategorizedImage(localFile, ItemId(it.id))
        }
    }
}
