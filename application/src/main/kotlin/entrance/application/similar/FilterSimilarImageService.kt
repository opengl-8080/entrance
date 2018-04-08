package entrance.application.similar

import entrance.domain.entry.AllEntryImages
import entrance.domain.entry.EntryImage
import entrance.domain.similar.SimilarImageFinder
import entrance.domain.similar.SimilarImageHandler
import org.springframework.stereotype.Component

@Component
class FilterSimilarImageService(
    private val similarImageFinder: SimilarImageFinder,
    private val similarImageHandler: SimilarImageHandler
) {
    
    fun filter(allEntryImages: AllEntryImages): List<EntryImage> {
        val saveTargetEntryImages = mutableListOf<EntryImage>()
        
        allEntryImages.forEachImages { entryImage -> 
            val similarImages = similarImageFinder.findSimilarImage(entryImage)
            
            if (similarImages.isEmpty()) {
                saveTargetEntryImages += entryImage
            } else {
                val result = similarImageHandler.handle(entryImage, similarImages)
                if (result.saveEntryImage) {
                    saveTargetEntryImages += entryImage
                }
            }
        }
        
        return saveTargetEntryImages
    }
}