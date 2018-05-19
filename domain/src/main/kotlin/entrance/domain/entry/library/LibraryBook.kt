package entrance.domain.entry.library

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.book.Book
import entrance.domain.base.item.book.BookName
import entrance.domain.base.item.image.ImageFile
import entrance.domain.entry.RegisteredDateTime
import entrance.domain.util.image.ImageResizer
import java.awt.image.BufferedImage


class LibraryBook (
    private val localDirectory: LocalDirectory,
    val relativePath: RelativePath
) {
    val name: BookName = BookName(localDirectory.name)
    val registeredDateTime: RegisteredDateTime = RegisteredDateTime.now()

    fun createThumbnail() {
        val firstFile = localDirectory.getFirstFile()
        val thumbnailImageFile = Book.thumbnailImageFile(localDirectory)
        ImageResizer.resize(firstFile.javaPath, thumbnailImageFile.javaPath, 200.0, BufferedImage.SCALE_FAST)
    }
}
