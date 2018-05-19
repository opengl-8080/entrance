package entrance.infrastructure.entry.book

import entrance.domain.entry.library.LibraryBook
import entrance.domain.entry.library.LibraryBookRepository
import entrance.infrastructure.database.book.BookTable
import entrance.infrastructure.database.book.BookTableDao
import entrance.infrastructure.database.item.ItemTable
import entrance.infrastructure.database.item.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbLibraryBookRepository (
        private val bookTableDao: BookTableDao,
        private val itemTableDao: ItemTableDao
): LibraryBookRepository {
    
    override fun save(libraryBook: LibraryBook) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = libraryBook.registeredDateTime.value
        itemTable.rank = 1
        itemTableDao.insert(itemTable)

        val bookTable = BookTable()
        bookTable.itemId = itemTable.id
        bookTable.name = libraryBook.name.value
        bookTable.path = libraryBook.relativePath.value
        bookTableDao.insert(bookTable)
    }
}
