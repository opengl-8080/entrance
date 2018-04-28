package entrance.domain.tag.category

data class AllTagCategories(
    val tagCategories: List<TagCategory>
) {
    
    private val map: Map<String, TagCategory> = mutableMapOf<String, TagCategory>().also { map ->
        tagCategories.forEach { tagCategory -> 
            map.put(tagCategory.name.value, tagCategory)
        }
    }
    
    fun get(name: String): TagCategory = map[name]!!

    fun get(name: TagCategoryName): TagCategory = map[name.value]!!
}