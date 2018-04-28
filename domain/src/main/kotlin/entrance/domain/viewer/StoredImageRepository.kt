package entrance.domain.viewer

import entrance.domain.RankCondition
import entrance.domain.tag.Tag

interface StoredImageRepository {
    
    fun find(tagList: List<Tag>, rankCondition: RankCondition): List<StoredImage>
    
    fun findNotTaggedImage(rankCondition: RankCondition): List<StoredImage>
    
    fun delete(storedImage: StoredImage)
}