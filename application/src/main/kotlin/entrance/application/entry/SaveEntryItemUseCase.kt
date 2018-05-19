package entrance.application.entry

import entrance.domain.entry.library.LibraryBookRepository
import entrance.domain.entry.entrance.EntryBook
import entrance.domain.entry.entrance.EntryImage
import entrance.domain.entry.library.LibraryImageRepository
import entrance.domain.entry.library.LibraryDirectory
import entrance.domain.entry.similar.SimilarImageIndexer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class SaveEntryItemUseCase (
    private val libraryDirectory: LibraryDirectory,
    private val libraryImageRepository: LibraryImageRepository,
    private val libraryBookRepository: LibraryBookRepository,
    private val similarImageIndexer: SimilarImageIndexer
) {
    private val logger = LoggerFactory.getLogger(SaveEntryItemUseCase::class.java)
    
    fun save(entryImage: EntryImage) {
        val libraryImage = libraryDirectory.move(entryImage).replaceToSmallImageIfTooLarge()
        libraryImageRepository.save(libraryImage)
        libraryImage.createThumbnail()
        similarImageIndexer.indexSimilarImage(libraryImage)
        
        logger.debug("entryImage={}, libraryImage={}", entryImage, libraryImage)
    }
    
    fun save(entryBook: EntryBook) {
        val libraryBook = libraryDirectory.move(entryBook)
        libraryBookRepository.save(libraryBook)
        libraryBook.createThumbnail()
    }
}