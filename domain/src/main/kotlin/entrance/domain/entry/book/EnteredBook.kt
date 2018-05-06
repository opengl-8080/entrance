package entrance.domain.entry.book

import entrance.domain.book.BaseBook
import entrance.domain.book.BookName
import entrance.domain.entry.RegisteredDateTime
import entrance.domain.util.file.Directory
import entrance.domain.util.file.RelativePath
import entrance.domain.util.image.ImageResizer
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Paths


class EnteredBook (
        val name: BookName,
        directory: Directory,
        val relativePath: RelativePath
): BaseBook(directory) {
    val registeredDateTime: RegisteredDateTime = RegisteredDateTime.now()
    
    fun createThumbnail() {
        val firstImage = Files.list(directory.path).sorted().findFirst().get()
        ImageResizer.resize(firstImage, Paths.get(thumbnailUri), 200.0, BufferedImage.SCALE_FAST)
    }
}
