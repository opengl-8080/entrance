package entrance.domain.similar

import entrance.domain.viewer.StoredImage


interface SimilarImageIndexDeleter {
    
    fun deleteIndex(storedImage: StoredImage)
}