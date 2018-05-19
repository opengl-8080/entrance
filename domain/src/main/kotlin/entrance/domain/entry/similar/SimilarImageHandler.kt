package entrance.domain.entry.similar

import entrance.domain.entry.entrance.EntryImage

interface SimilarImageHandler {
    
    fun handle(entryImage: EntryImage, similarImages: SimilarImages): SimilarImageHandleResult
}