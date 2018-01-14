package entrance.application.entry

import entrance.domain.entry.EnteredImageRepository
import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SaveEnteredItemService(
    private val libraryDirectory: LibraryDirectory,
    private val enteredImageRepository: EnteredImageRepository
) {
    private val logger = LoggerFactory.getLogger(SaveEnteredItemService::class.java)
    
    @Transactional
    fun save(entryImage: EntryImage) {
        val enteredImage = libraryDirectory.move(entryImage)
        enteredImageRepository.save(enteredImage)
        
        logger.debug("entryImage={}, enteredImage={}", entryImage, enteredImage)
    }
}
