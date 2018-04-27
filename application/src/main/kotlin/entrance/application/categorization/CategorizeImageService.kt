package entrance.application.categorization

import entrance.domain.categorization.TaggedImage
import entrance.domain.categorization.TaggedImageRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CategorizeImageService (
    private val taggedImageRepository: TaggedImageRepository
) {
    
    fun categorize(modifiedImages: List<TaggedImage>) {
        modifiedImages.forEach { taggedImage ->
            taggedImageRepository.save(taggedImage)
        }
    }
}