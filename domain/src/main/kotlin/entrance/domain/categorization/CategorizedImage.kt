package entrance.domain.categorization

import entrance.domain.ItemId
import entrance.domain.tag.Tag

data class CategorizedImage (
    val itemId: ItemId,
    val mainTag: Tag
)