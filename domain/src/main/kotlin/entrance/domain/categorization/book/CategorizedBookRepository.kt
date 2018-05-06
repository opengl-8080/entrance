package entrance.domain.categorization.book

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet


interface CategorizedBookRepository {

    fun findNotTaggedBooks(rankCondition: RankCondition): List<CategorizedBook>

    fun findTaggedBooks(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<CategorizedBook>

    fun save(categorizedBook: CategorizedBook)
}