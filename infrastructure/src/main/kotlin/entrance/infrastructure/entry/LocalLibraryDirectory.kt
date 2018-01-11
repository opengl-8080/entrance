package entrance.infrastructure.entry

import entrance.domain.config.EntranceHome
import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.entry.EnteredImage
import entrance.domain.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class LocalLibraryDirectory(
    entranceHome: EntranceHome
): LibraryDirectory {

    private val dir = entranceHome.initDir(RelativePath("library"))
    
    private val yearFormatter = DateTimeFormatter.ofPattern("uuuu")
    private val monthFormatter = DateTimeFormatter.ofPattern("MM")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    
    override fun move(entryImage: EntryImage): EnteredImage {
        val today = LocalDate.now()
        val yearDir = dir.resolveDir(RelativePath(today.format(yearFormatter)))
        val monthDir = yearDir.resolveDir(RelativePath(today.format(monthFormatter)))
        val dateDir = monthDir.resolveDir(RelativePath(today.format(dateFormatter)))
        dateDir.createIfNotExists()

        val fileName = UUID.randomUUID().toString() + "." + entryImage.extension
        val outputFile = dateDir.resolveFile(RelativePath(fileName))
        
        Files.move(entryImage.path, outputFile.path, StandardCopyOption.ATOMIC_MOVE)

        val relativePath = dir.relativize(outputFile)
        return EnteredImage(relativePath)
    }
    
    private fun createDirectoriesIfNotExists(dir: Path) {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir)
        }
    }
    
    
}