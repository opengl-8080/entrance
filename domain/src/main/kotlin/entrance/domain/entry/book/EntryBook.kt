package entrance.domain.entry.book

import entrance.domain.book.BookName
import entrance.domain.util.file.Directory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class EntryBook (
        val name: BookName,
        val path: Path
) {
    
    fun moveTo(outputDir: Directory) {
        Files.move(path, outputDir.path, StandardCopyOption.ATOMIC_MOVE)
    }
}
