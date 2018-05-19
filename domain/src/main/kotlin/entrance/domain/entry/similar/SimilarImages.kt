package entrance.domain.entry.similar


class SimilarImages (
    private val images: List<SimilarImage>
) {
    val size: Int = images.size
    
    operator fun get(index: Int): SimilarImage = images[index]
    
    fun isNotEmpty(): Boolean = images.isNotEmpty()
}