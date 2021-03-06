package entrance.infrastructure.viewer.book

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.domain.base.file.RelativePath
import entrance.domain.book.BookName
import entrance.domain.entry.library.LibraryDirectory
import entrance.domain.tag.SelectedTagSet
import entrance.domain.tag.TagRepository
import entrance.domain.viewer.book.StoredBook
import entrance.domain.viewer.book.StoredBookRepository
import entrance.infrastructure.database.book.BookTableDao
import entrance.infrastructure.database.item.ItemTableDao
import entrance.infrastructure.database.tag.ItemTagTableDao
import entrance.infrastructure.database.view.BookItemView
import org.springframework.stereotype.Component

@Component
class RdbStoredBookRepository(
    private val itemTableDao: ItemTableDao,
    private val bookTableDao: BookTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val tagRepository: TagRepository
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
        val tags = tagRepository.findByItemId(ItemId(bookItemView.id))
        val rank = Rank.of(bookItemView.rank)
        return StoredBook(ItemId(bookItemView.id), bookName, relativePath, directory, tags, rank)
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