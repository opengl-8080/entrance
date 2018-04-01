package entrance.domain.categorization

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.file.LocalFile
import entrance.domain.image.Image
import entrance.domain.tag.Tag

/**
 * 未分類画像
 */
class NotCategorizedImage (
    val file: LocalFile,
    val itemId: ItemId
): ImageFile {

    override val stringPath: String
        get() = file.uriString
    
    fun categorize(mainTag: Tag) = CategorizedImage(itemId, mainTag)
}