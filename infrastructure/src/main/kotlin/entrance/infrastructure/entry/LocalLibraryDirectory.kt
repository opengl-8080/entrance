package entrance.infrastructure.entry

import entrance.domain.entry.LibraryDirectory
import entrance.domain.entry.book.EnteredBook
import entrance.domain.entry.book.EntryBook
import entrance.domain.entry.image.EnteredImage
import entrance.domain.entry.image.EntryImage
import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.Directory
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class LocalLibraryDirectory(
    entranceHome: EntranceHome
): LibraryDirectory {

    private val logger = LoggerFactory.getLogger(LocalLibraryDirectory::class.java)

    private val dir: Directory = entranceHome.initDir(RelativePath("library"))
    
    private val yearFormatter = DateTimeFormatter.ofPattern("uuuu")
    private val monthFormatter = DateTimeFormatter.ofPattern("MM")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")

    override fun resolveFile(relativePath: RelativePath): LocalFile {
        return dir.resolveFile(relativePath)
    }
    
    override fun resolveDirectory(relativePath: RelativePath): Directory {
        return dir.resolveDir(relativePath)
    }
    
    override fun move(entryBook: EntryBook): EnteredBook {
        val dateDir = dateDir()
        val outputDir = dateDir.resolveDir(RelativePath(entryBook.name.value))
        
        entryBook.moveTo(outputDir)
        val relativeDirPath = dir.relativize(outputDir)
        
        return EnteredBook(entryBook.name, outputDir, relativeDirPath)
    }
    
    private fun dateDir(): Directory {
        val today = LocalDate.now()
        val yearDir = dir.resolveDir(RelativePath(today.format(yearFormatter)))
        val monthDir = yearDir.resolveDir(RelativePath(today.format(monthFormatter)))
        val dateDir = monthDir.resolveDir(RelativePath(today.format(dateFormatter)))
        dateDir.createIfNotExists()
        
        return dateDir
    }
    
    override fun move(entryImage: EntryImage): EnteredImage {
        val dateDir = dateDir()
        
        val fileName = UUID.randomUUID().toString() + "." + entryImage.extension
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
        
        val relativePath = dir.relativize(outputFile)
        val enteredImage = EnteredImage(outputFile, relativePath)
        
        return if (enteredImage.isTooLarge()) {
            enteredImage.createSmallSizeImage()
        } else {
            enteredImage
        }
    }
}