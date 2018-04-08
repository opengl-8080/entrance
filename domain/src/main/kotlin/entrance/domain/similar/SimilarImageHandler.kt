package entrance.domain.similar

import entrance.domain.entry.EntryImage

interface SimilarImageHandler {
    
    fun handle(entryImage: EntryImage, similarImages: List<SimilarImage>): SimilarImageHandleResult
}