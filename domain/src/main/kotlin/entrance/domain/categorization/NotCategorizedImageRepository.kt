package entrance.domain.categorization


interface NotCategorizedImageRepository {
    
    fun save(notCategorizedImage: NotCategorizedImage)
}