package entrance.infrastructure.entry

import entrance.domain.book.BookName
import entrance.domain.entry.EntryDirectory
import entrance.domain.entry.book.AllEntryBooks
import entrance.domain.entry.book.EntryBook
import entrance.domain.entry.image.AllEntryImages
import entrance.domain.entry.image.EntryImage
import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

@Component
class LocalEntryDirectory (
        entranceHome: EntranceHome
): EntryDirectory {

    private val dir = entranceHome.initDir(RelativePath("entry"))

    override fun readAllImages(): AllEntryImages {
        val images = Files.list(dir.path)
                .map { LocalFile(it) }
                .filter { it.isImage() }
                .map { EntryImage(it) }
                .toList()

        return AllEntryImages(images)
    }

    override fun readAllBooks(): AllEntryBooks {
        val books = Files.list(dir.path)
                .filter { Files.isDirectory(it) }
                .map { dir ->
                    val name = BookName(dir.fileName.toString())
                   EntryBook(name, dir) 
                }
                .toList()

        return AllEntryBooks(books)
    }

    override fun resolveFile(relativePath: RelativePath): LocalFile = dir.resolveFile(relativePath)
    override fun path(): Path = dir.path

}