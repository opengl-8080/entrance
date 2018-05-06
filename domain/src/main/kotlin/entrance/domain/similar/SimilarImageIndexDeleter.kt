package entrance.domain.similar

import entrance.domain.viewer.image.StoredImage


interface SimilarImageIndexDeleter {
    
    fun deleteIndex(storedImage: StoredImage)
}