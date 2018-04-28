package entrance.domain.tag.category


class TagCategory (
    val id: TagCategoryId,
    val name: TagCategoryName
) {
    
    fun isUnmodifiable(): Boolean {
        return name == TagCategoryName.OTHERS
    }
}