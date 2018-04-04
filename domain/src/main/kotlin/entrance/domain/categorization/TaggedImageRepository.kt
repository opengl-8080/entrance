package entrance.domain.categorization

import entrance.domain.tag.Tag

interface TaggedImageRepository {
    
    fun findNotTaggedImages(): List<NotTaggedImage>
    
    fun findTaggedImages(tagList: List<Tag>): List<TaggedImage>
    
    fun save(taggedImage: TaggedImage)
}