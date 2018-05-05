package entrance.domain.categorization

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet

interface TaggedImageRepository {
    
    fun findNotTaggedImages(rankCondition: RankCondition): List<TaggedImage>
    
    fun findTaggedImages(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<TaggedImage>
    
    fun save(taggedImage: TaggedImage)
}