package entrance.infrastructure.entry.book

import entrance.domain.entry.book.EnteredBook
import entrance.domain.entry.book.EnteredBookRepository
import entrance.infrastructure.database.book.BookTable
import entrance.infrastructure.database.book.BookTableDao
import entrance.infrastructure.database.item.ItemTable
import entrance.infrastructure.database.item.ItemTableDao
import org.springframework.stereotype.Component

@Component
class RdbEnteredBookRepository (
        private val bookTableDao: BookTableDao,
        private val itemTableDao: ItemTableDao
): EnteredBookRepository {
    
    override fun save(enteredBook: EnteredBook) {
        val itemTable = ItemTable()
        itemTable.registeredDateTime = enteredBook.registeredDateTime.value
        itemTable.rank = 1
        itemTableDao.insert(itemTable)

        val bookTable = BookTable()
        bookTable.itemId = itemTable.id
        bookTable.name = enteredBook.name.value
        bookTable.path = enteredBook.relativePath.asString()
        bookTableDao.insert(bookTable)
    }
}
