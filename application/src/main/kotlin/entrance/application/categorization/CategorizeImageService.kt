package entrance.application.categorization

import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import entrance.domain.tag.Tag
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CategorizeImageService (
    private val taggedImageRepository: TaggedImageRepository
) {
    
    fun categorize(assignedImages: Map<Tag, List<TaggedImage>>) {
        
    }
}