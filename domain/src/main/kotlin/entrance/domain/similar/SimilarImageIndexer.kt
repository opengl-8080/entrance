package entrance.domain.similar

import entrance.domain.entry.EnteredImage


interface SimilarImageIndexer {
    
    fun indexSimilarImage(enteredImage: EnteredImage)
}