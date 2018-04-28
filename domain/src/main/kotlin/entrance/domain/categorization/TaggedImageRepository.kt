package entrance.domain.categorization

import entrance.domain.RankCondition
import entrance.domain.tag.Tag

interface TaggedImageRepository {
    
    fun findNotTaggedImages(rankCondition: RankCondition): List<TaggedImage>
    
    fun findTaggedImages(tagList: List<Tag>, rankCondition: RankCondition): List<TaggedImage>
    
    fun save(taggedImage: TaggedImage)
}