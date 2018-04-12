package entrance.domain.viewer

import entrance.domain.tag.Tag

interface StoredImageRepository {
    
    fun find(tagList: List<Tag>): List<StoredImage>
    
    fun findNotTaggedImage(): List<StoredImage>
    
    fun delete(storedImage: StoredImage)
}