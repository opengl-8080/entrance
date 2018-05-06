package entrance.domain.viewer.image

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet

interface StoredImageRepository {
    
    fun find(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<StoredImage>
    
    fun findNotTaggedImage(rankCondition: RankCondition): List<StoredImage>
    
    fun delete(storedImage: StoredImage)
}