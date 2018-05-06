package entrance.domain.similar

import entrance.domain.entry.image.EnteredImage


interface SimilarImageIndexer {
    
    fun indexSimilarImage(enteredImage: EnteredImage)
}