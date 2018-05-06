package entrance.infrastructure.viewer.book

import entrance.domain.book.BookName
import entrance.domain.ItemId
import entrance.domain.RankCondition
import entrance.domain.entry.LibraryDirectory
import entrance.domain.tag.SelectedTagSet
import entrance.domain.util.file.RelativePath
import entrance.domain.viewer.book.StoredBook
import entrance.domain.viewer.book.StoredBookRepository
import entrance.infrastructure.database.view.BookItemView
import entrance.infrastructure.database.book.BookTableDao
import entrance.infrastructure.database.item.ItemTableDao
import entrance.infrastructure.database.tag.ItemTagTableDao
import org.springframework.stereotype.Component

@Component
class RdbStoredBookRepository(
        private val itemTableDao: ItemTableDao,
        private val bookTableDao: BookTableDao,
        private val itemTagTableDao: ItemTagTableDao,
        private val libraryDirectory: LibraryDirectory
): StoredBookRepository {

    override fun find(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<StoredBook> {
        val categorizedTagList = bookTableDao.findTaggedBooksByTagIdList(selectedTagSet.idList, rankCondition.min.value, rankCondition.max.value)
        return categorizedTagList.map { it -> toStoredBook(it) }
    }
    
    override fun findNotTaggedBook(rankCondition: RankCondition): List<StoredBook> {
        val imageItemViewList = bookTableDao.findNotTaggedBooks(rankCondition.min.value, rankCondition.max.value)
        return imageItemViewList.map { it -> toStoredBook(it) }
    }
    
    private fun toStoredBook(bookItemView: BookItemView): StoredBook {
        val relativePath = RelativePath(bookItemView.path)
        val directory = libraryDirectory.resolveDirectory(relativePath)
        val bookName = BookName(bookItemView.name)
        return StoredBook(ItemId(bookItemView.id), bookName, relativePath, directory)
    }

    override fun delete(storedBook: StoredBook) {
        val itemTagTableList = itemTagTableDao.findByItemId(storedBook.itemId.value)

        itemTagTableList.forEach { itemTagTableDao.delete(it) }

        val imageTable = bookTableDao.find(storedBook.itemId.value)
        bookTableDao.delete(imageTable)

        val itemTable = itemTableDao.find(storedBook.itemId.value)
        itemTableDao.delete(itemTable)
    }
}