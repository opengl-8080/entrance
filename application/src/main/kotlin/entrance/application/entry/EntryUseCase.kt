package entrance.application.entry

import entrance.domain.entry.entrance.EntryBooks
import entrance.domain.entry.entrance.EntryImages
import entrance.domain.entry.similar.SearchSimilarImageService
import entrance.domain.entry.similar.SimilarImageHandleResult
import entrance.domain.entry.similar.SimilarImageHandler
import org.springframework.stereotype.Component

@Component
class EntryUseCase (
    private val searchSimilarImageService: SearchSimilarImageService,
    private val saveEntryItemUseCase: SaveEntryItemUseCase,
    private val similarImageHandler: SimilarImageHandler
) {
    
    fun execute(entryImages: EntryImages, entryBooks: EntryBooks, progressListener: (() -> Unit) -> Unit) {
        var cancelled = false
        val cancel = {
            cancelled = true
        }
        
        entryImages.forEach { entryImage -> 
            val similarImages = searchSimilarImageService.search(entryImage)
            
            val result = if (similarImages.isNotEmpty()) {
                similarImageHandler.handle(entryImage, similarImages)
            } else {
                SimilarImageHandleResult.save()
            }
            
            if (result.saveEntryImage) {
                saveEntryItemUseCase.save(entryImage)
            }

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
