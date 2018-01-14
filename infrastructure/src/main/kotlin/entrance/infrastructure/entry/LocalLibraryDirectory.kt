package entrance.infrastructure.entry

import entrance.domain.config.EntranceHome
import entrance.domain.entry.EnteredImage
import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class LocalLibraryDirectory(
    entranceHome: EntranceHome
): LibraryDirectory {
    private val logger = LoggerFactory.getLogger(LocalLibraryDirectory::class.java)

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
        var retryCount = 0

        while (true) {
            TimeUnit.MILLISECONDS.sleep(500)
            try {
                Files.move(entryImage.path, outputFile.path, StandardCopyOption.ATOMIC_MOVE)
                break
            } catch (e: FileSystemException) {
                logger.warn(e.message)
                
                retryCount++
                if (10 < retryCount) {
                    throw e
                }
            }
        }

        val relativePath = dir.relativize(outputFile)
        return EnteredImage(relativePath)
    }
}