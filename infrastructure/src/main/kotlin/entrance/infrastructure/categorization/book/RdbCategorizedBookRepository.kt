package entrance.infrastructure.categorization.book

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.RankCondition
import entrance.domain.categorization.book.CategorizedBook
import entrance.domain.categorization.book.CategorizedBookRepository
import entrance.domain.entry.LibraryDirectory
import entrance.domain.tag.*
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryId
import entrance.domain.tag.category.TagCategoryName
import entrance.domain.util.file.RelativePath
import entrance.infrastructure.database.book.BookTableDao
import entrance.infrastructure.database.item.ItemTableDao
import entrance.infrastructure.database.tag.ItemTagTable
import entrance.infrastructure.database.tag.ItemTagTableDao
import entrance.infrastructure.database.tag.TagTableDao
import entrance.infrastructure.database.tag.category.TagCategoryTableDao
import entrance.infrastructure.database.view.BookItemView
import org.springframework.stereotype.Component

@Component
class RdbCategorizedBookRepository(
    private val imageTableDao: BookTableDao,
    private val itemTagTableDao: ItemTagTableDao,
    private val libraryDirectory: LibraryDirectory,
    private val tagTableDao: TagTableDao,
    private val itemTableDao: ItemTableDao,
    private val tagCategoryTableDao: TagCategoryTableDao
): CategorizedBookRepository {

    override fun findNotTaggedBooks(rankCondition: RankCondition): List<CategorizedBook> {
        return imageTableDao.findNotTaggedBooks(rankCondition.min.value, rankCondition.max.value)
                .map { bookItemView -> toCategorizedBook(bookItemView, emptySet()) }
    }

    override fun findTaggedBooks(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<CategorizedBook> {
        return imageTableDao.findTaggedBooksByTagIdList(selectedTagSet.idList, rankCondition.min.value, rankCondition.max.value)
                .map { bookItemView ->
                    val relationalTagSet = tagTableDao.findByItemId(bookItemView.id).map { tagTable ->
                        val tagId = TagId(tagTable.id)
                        val tagName = TagName(tagTable.name)
                        val tagFilterWord = TagFilterWord(tagTable.filterWord)

                        val tagCategoryTable = tagCategoryTableDao.find(tagTable.tagCategoryId)
                        val tagCategoryId = TagCategoryId(tagCategoryTable.id)
                        val tagCategoryName = TagCategoryName(tagCategoryTable.name)
                        val tagCategory = TagCategory(id = tagCategoryId, name = tagCategoryName)

                        Tag(id = tagId, name = tagName, filterWord = tagFilterWord, tagCategory = tagCategory)
                    }.toSet()

                    toCategorizedBook(bookItemView, relationalTagSet)
                }
    }

    private fun toCategorizedBook(bookItemView: BookItemView, tagSet: Set<Tag>): CategorizedBook {
        val relativePath = RelativePath(bookItemView.path)
        val directory = libraryDirectory.resolveDirectory(relativePath)
        return CategorizedBook(ItemId(bookItemView.id), directory=directory, tagSet = tagSet, rank=Rank.of(bookItemView.rank))
    }

    override fun save(categorizedBook: CategorizedBook) {
        val itemId = categorizedBook.itemId.value

        categorizedBook.newAssignedTagSet.tagSet.forEach { newAssignedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = newAssignedTag.id.value
            itemTagTableDao.insert(itemTagTable)
        }

        categorizedBook.releasedTagSet.tagSet.forEach { releasedTag ->
            val itemTagTable = ItemTagTable()
            itemTagTable.itemId = itemId
            itemTagTable.tagId = releasedTag.id.value
            itemTagTableDao.delete(itemTagTable)
        }

        val itemTable = itemTableDao.find(itemId)
        itemTable.rank = categorizedBook.rank.value
        itemTableDao.update(itemTable)
    }
}