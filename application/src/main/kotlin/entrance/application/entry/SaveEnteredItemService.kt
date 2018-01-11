package entrance.application.entry

import entrance.domain.entry.EnteredImageRepository
import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SaveEnteredItemService(
    private val libraryDirectory: LibraryDirectory,
    private val enteredImageRepository: EnteredImageRepository
) {
    
    @Transactional
    fun save(entryImage: EntryImage) {
        val enteredImage = libraryDirectory.move(entryImage)
        enteredImageRepository.save(enteredImage)
    }
}
