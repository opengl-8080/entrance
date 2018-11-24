package entrance.infrastructure.entry.image

import entrance.domain.entry.library.LibraryImageRepository
import entrance.domain.entry.library.LibraryImage
import entrance.infrastructure.database.image.ImageTable
import entrance.infrastructure.database.image.ImageTableDao
import entrance.infrastructure.database.item.ItemTable
import entrance.infrastructure.database.item.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbLibraryImageRepository (
        private val imageTableDao: ImageTableDao,
        private val itemTableDao: ItemTableDao
): LibraryImageRepository {
    
    override fun save(libraryImage: LibraryImage) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = libraryImage.registeredDateTime.value
        itemTable.rank = 1
        itemTableDao.insert(itemTable)

        val imageTable = ImageTable()
        imageTable.itemId = itemTable.id
        imageTable.path = libraryImage.relativePath.value
        imageTable.width = libraryImage.width
        imageTable.height = libraryImage.height
        imageTableDao.insert(imageTable)
    }
}