package entrance.application.categorization

import entrance.domain.categorization.CategorizedImageRepository
import entrance.domain.categorization.NotCategorizedImage
import entrance.domain.tag.Tag
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CategorizeImageService (
    private val categorizedImageRepository: CategorizedImageRepository
) {
    
    fun categorize(assignedImages: Map<Tag, List<NotCategorizedImage>>) {
        assignedImages.forEach { tag, images -> 
            images.forEach { image ->
                val categorizedImage = image.categorize(tag)
                categorizedImageRepository.save(categorizedImage)
            }
        }
    }
}