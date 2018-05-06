package entrance.domain.viewer.book

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet

interface StoredBookRepository {

    fun find(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<StoredBook>

    fun findNotTaggedBook(rankCondition: RankCondition): List<StoredBook>

    fun delete(storedBook: StoredBook)
}