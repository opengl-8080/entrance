package entrance.application.categorization

import entrance.domain.categorization.book.CategorizedBook
import entrance.domain.categorization.book.CategorizedBookRepository
import entrance.domain.categorization.image.CategorizedImage
import entrance.domain.categorization.image.CategorizedImageRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CategorizeService (
    private val categorizedImageRepository: CategorizedImageRepository,
    private val categorizedBookRepository: CategorizedBookRepository
) {
    
    fun categorizeImages(modifiedImages: List<CategorizedImage>) {
        modifiedImages.forEach { taggedImage ->
            categorizedImageRepository.save(taggedImage)
        }
    }
    
    fun categorizeBooks(modifiedBooks: List<CategorizedBook>) {
        modifiedBooks.forEach { taggedBook ->
            categorizedBookRepository.save(taggedBook)
        }
    }
}