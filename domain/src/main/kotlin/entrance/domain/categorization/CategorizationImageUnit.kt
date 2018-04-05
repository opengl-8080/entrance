package entrance.domain.categorization

class CategorizationImageUnit (
    val imageList: Set<TaggedImage>
) {
    
    val commonAssignedTags = CommonAssignedTagSet(
        imageList.map { it.tagSet }.reduce { tmp, tagSet -> tmp.intersect(tagSet) }.toSet()
    )
}
