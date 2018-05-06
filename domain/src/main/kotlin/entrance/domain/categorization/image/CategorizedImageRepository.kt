package entrance.domain.categorization.image

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet

interface CategorizedImageRepository {
    
    fun findNotTaggedImages(rankCondition: RankCondition): List<CategorizedImage>
    
    fun findTaggedImages(selectedTagSet: SelectedTagSet, rankCondition: RankCondition): List<CategorizedImage>
    
    fun save(categorizedImage: CategorizedImage)
}