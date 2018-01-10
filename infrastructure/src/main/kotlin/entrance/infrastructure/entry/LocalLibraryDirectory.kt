package entrance.infrastructure.entry

import entrance.domain.config.EntranceHome
import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.entry.SavedImage
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

    private val dir = entranceHome.path().resolve("library").toAbsolutePath().also(::createDirectoriesIfNotExists)
    
    private val yearMonthFormatter = DateTimeFormatter.ofPattern("uuuuMM")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    
    override fun move(entryImage: EntryImage): SavedImage {
        val today = LocalDate.now()
        val yearMonthDir = dir.resolve(today.format(yearMonthFormatter)).also(::createDirectoriesIfNotExists)
        val dateDir = yearMonthDir.resolve(today.format(dateFormatter)).also(::createDirectoriesIfNotExists)

        val fileName = UUID.randomUUID().toString() + "." + entryImage.extension
        val toPath = dateDir.resolve(fileName)
        
        Files.move(entryImage.path, toPath, StandardCopyOption.ATOMIC_MOVE)

        val relativePath = RelativePath(dir.relativize(toPath))
        return SavedImage(relativePath)
    }
    
    private fun createDirectoriesIfNotExists(dir: Path) {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir)
        }
    }
    
    
}