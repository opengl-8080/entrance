package entrance.domain.categorization.book

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.ThumbnailImage
import entrance.domain.base.file.LocalDirectory
import entrance.domain.book.BaseBook
import entrance.domain.categorization.CategorizedItem
import entrance.domain.tag.Tag
import entrance.domain.viewer.book.BookImage
import java.net.URI


class CategorizedBook(
    val itemId: ItemId,
    directory: LocalDirectory,
    tagSet: Set<Tag>,
    rank: Rank
): CategorizedItem(tagSet, rank), ThumbnailImage {
    private val book = object: BaseBook(directory) {}

    override val thumbnailUri: URI
        get() = book.thumbnailUri
    
    val images: List<BookImage> = book.images
}
