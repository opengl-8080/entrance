package entrance.domain.image

import entrance.domain.tag.Tag

interface ImageRepository {
    
    fun find(tagList: List<Tag>): List<Image>
    
    fun findNotTaggedImage(): List<Image>
    
    fun delete(image: Image)
}