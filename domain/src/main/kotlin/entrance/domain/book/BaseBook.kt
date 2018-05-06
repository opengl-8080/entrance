package entrance.domain.book

import entrance.domain.ThumbnailImage
import entrance.domain.util.file.Directory
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import entrance.domain.viewer.book.BookImage
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList


abstract class BaseBook(val directory: Directory): ThumbnailImage {
    
    override val thumbnailUri: URI = directory.resolveFile(RelativePath("thumbnail.jpg")).path.toUri()
    
    open val images: List<BookImage> = Files.list(directory.path)
                                    .filter { isNotThumbnail(it) }
                                    .map { LocalFile(it) }
                                    .filter { it.isImage() }
                                    .sorted()
                                    .toList()
                                    .map { BookImage(it) }
    
    private fun isNotThumbnail(path: Path): Boolean = path.toUri() != thumbnailUri
}
