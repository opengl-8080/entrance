package entrance.domain.similar

import entrance.domain.entry.EntryImage

interface SimilarImageFinder {
    
    fun findSimilarImage(entryImage: EntryImage): List<SimilarImage>
}