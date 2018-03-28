package entrance.domain.categorization


interface NotCategorizedImageRepository {
    
    fun loadAll(): List<NotCategorizedImage>
}