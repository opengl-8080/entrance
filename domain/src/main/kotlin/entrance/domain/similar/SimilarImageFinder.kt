package entrance.domain.similar

import entrance.domain.entry.image.EntryImage

interface SimilarImageFinder {
    
    fun findSimilarImage(entryImage: EntryImage): List<SimilarImage>
}