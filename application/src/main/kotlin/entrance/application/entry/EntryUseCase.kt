package entrance.application.entry

import entrance.domain.entry.entrance.EntryBooks
import entrance.domain.entry.entrance.EntryImages
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EntryUseCase (
    private val saveEntryItemUseCase: SaveEntryItemUseCase
) {
    private val logger = LoggerFactory.getLogger(EntryUseCase::class.java)
    
    fun execute(entryImages: EntryImages, entryBooks: EntryBooks, progressListener: (() -> Unit) -> Unit) {
        if (logger.isDebugEnabled) {
            logger.debug("begin entry. entryImages.size={}", entryImages.size)
        }
        
        var cancelled = false
        val cancel = {
            cancelled = true
        }
        
        entryImages.forEach { entryImage -> 
            saveEntryItemUseCase.save(entryImage)

            progressListener(cancel)
            if (cancelled) {
                return@forEach
            }
        }

        entryBooks.forEach { entryBook ->
            saveEntryItemUseCase.save(entryBook)

            progressListener(cancel)
            if (cancelled) {
                return@forEach
            }
        }
    }
}
