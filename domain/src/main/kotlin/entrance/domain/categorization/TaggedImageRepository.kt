package entrance.domain.categorization

import entrance.domain.tag.Tag

interface TaggedImageRepository {
    
    fun findNotTaggedImages(): List<TaggedImage>
    
    fun findTaggedImages(tagList: List<Tag>): List<TaggedImage>
    
    fun save(taggedImage: TaggedImage, newAssignedTagSet: NewAssignedTagSet, releasedTagSet: ReleasedTagSet)
}