package entrance.application.entry

import entrance.domain.entry.image.EnteredImageRepository
import entrance.domain.entry.image.EntryImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.entry.book.EnteredBookRepository
import entrance.domain.entry.book.EntryBook
import entrance.domain.similar.SimilarImageIndexer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class SaveEnteredItemService(
    private val libraryDirectory: LibraryDirectory,
    private val enteredImageRepository: EnteredImageRepository,
    private val similarImageIndexer: SimilarImageIndexer,
    private val enteredBookRepository: EnteredBookRepository
) {
    private val logger = LoggerFactory.getLogger(SaveEnteredItemService::class.java)

    fun save(entryImage: EntryImage) {
        val enteredImage = libraryDirectory.move(entryImage)
        enteredImageRepository.save(enteredImage)
        enteredImage.createThumbnail()
        similarImageIndexer.indexSimilarImage(enteredImage)
        
        logger.debug("entryImage={}, enteredImage={}", entryImage, enteredImage)
    }
    
    fun save(entryBook: EntryBook) {
        val enteredBook = libraryDirectory.move(entryBook)
        enteredBookRepository.save(enteredBook)
        enteredBook.createThumbnail()
    }
}
