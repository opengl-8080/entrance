package entrance.domain.book

import entrance.domain.ThumbnailImage
import entrance.domain.base.file.AbsolutePath
import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.image.ImageFile
import entrance.domain.viewer.book.BookImage
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList


abstract class BaseBook(val directory: LocalDirectory): ThumbnailImage {
    
    override val thumbnailUri: URI = directory.resolveFile(RelativePath("thumbnail.jpg")).javaPath.toUri()
    
    open val images: List<BookImage> = Files.list(directory.javaPath)
                                    .filter { isNotThumbnail(it) }
                                    .map { AbsolutePath(it) }
                                    .map { LocalFile(it) }
                                    .filter { ImageFile.isImageFile(it) }
                                    .sorted()
                                    .toList()
                                    .map { BookImage(it) }
    
    private fun isNotThumbnail(path: Path): Boolean = path.toUri() != thumbnailUri
}
