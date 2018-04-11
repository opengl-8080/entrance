package entrance.domain.similar

import entrance.domain.image.Image


interface SimilarImageIndexDeleter {
    
    fun deleteIndex(image: Image)
}