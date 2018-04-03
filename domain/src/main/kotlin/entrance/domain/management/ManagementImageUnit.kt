package entrance.domain.management


class ManagementImageUnit (
    private val imageList: List<ManagedImage>
) {
    
    val tagUnit: ManagedTagUnit
        get() {
            val allTagList = imageList.flatMap { it.tagList }
            return ManagedTagUnit(allTagList)
        }
}