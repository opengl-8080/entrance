package entrance.domain

interface ImageRepository {
    
    fun find(id: ItemId): ImageItem
    
    fun save(imageItem: ImageItem)
}
