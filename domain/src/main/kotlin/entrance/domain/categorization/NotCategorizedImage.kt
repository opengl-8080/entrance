package entrance.domain.categorization

import entrance.domain.ItemId
import entrance.domain.file.LocalFile
import entrance.domain.tag.Tag

/**
 * 未分類画像
 */
class NotCategorizedImage (
    file: LocalFile,
    val itemId: ItemId
) {

    val uriString: String = file.uriString
    
    fun categorize(mainTag: Tag) = CategorizedImage(itemId, mainTag)
}