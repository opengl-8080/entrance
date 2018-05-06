package entrance.application.similar

import entrance.domain.entry.image.EntryImage
import entrance.domain.similar.SimilarImageFinder
import entrance.domain.similar.SimilarImageHandler
import org.springframework.stereotype.Component

@Component
class FilterSimilarImageService(
    private val similarImageFinder: SimilarImageFinder,
    private val similarImageHandler: SimilarImageHandler
) {
    
    fun decideToSave(entryImage: EntryImage): Boolean {
        val similarImages = similarImageFinder.findSimilarImage(entryImage)
        
        return similarImages.isEmpty()
                || similarImageHandler.handle(entryImage, similarImages).saveEntryImage
    }
}