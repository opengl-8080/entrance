package entrance.domain.categorization

interface CategorizedImageRepository {
    
    fun save(categorizedImage: CategorizedImage);
}