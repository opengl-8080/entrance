package entrance.domain.entry.library

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.entry.entrance.EntryBook
import entrance.domain.entry.entrance.EntryImage
import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.DeprecatedDirectory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class LibraryDirectory (
    entranceHome: EntranceHome
) {
    private val logger = LoggerFactory.getLogger(LibraryDirectory::class.java)
    
    private val dir = entranceHome.resolveDirectory(RelativePath("library")).createIfNotExists()
    
    private val yearFormatter = DateTimeFormatter.ofPattern("uuuu")
    private val monthFormatter = DateTimeFormatter.ofPattern("MM")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    
    fun resolveFile(relativePath: RelativePath): LocalFile {
        return dir.resolveFile(relativePath)
    }

    fun resolveDirectory(relativePath: RelativePath): LocalDirectory {
        return dir.resolveDirectory(relativePath)
    }
    
    fun move(entryImage: EntryImage): LibraryImage {
        val dateDir = dateDir()

        val fileName = UUID.randomUUID().toString() + "." + entryImage.localFile.extension
        val outputFile = dateDir.resolveFile(RelativePath(fileName))
        var retryCount = 0

        while (true) {
            TimeUnit.MILLISECONDS.sleep(500)
            try {
                entryImage.moveTo(outputFile)
                break
            } catch (e: FileSystemException) {
                logger.warn(e.message)

                retryCount++
                if (10 < retryCount) {
                    throw e
                }
            }
        }

        val relativePath = dir.relativize(outputFile.absolutePath)
        return LibraryImage(outputFile, relativePath).replaceToSmallImageIfTooLarge()
    }
    
    fun move(entryBook: EntryBook): LibraryBook {
        val dateDir = dateDir()
        
        val movedDirectory = dateDir.move(entryBook.localDirectory)
        val relativePath = dir.relativize(movedDirectory.absolutePath)
        return LibraryBook(movedDirectory, relativePath)
    }
    
    private fun dateDir(): LocalDirectory {
        val today = LocalDate.now()
        val yearDir = dir.resolveDirectory(RelativePath(today.format(yearFormatter)))
        val monthDir = yearDir.resolveDirectory(RelativePath(today.format(monthFormatter)))
        val dateDir = monthDir.resolveDirectory(RelativePath(today.format(dateFormatter)))
        dateDir.createIfNotExists()

        return dateDir
    }
}