package entrance.domain.entry.entrance


class EntryImages (
    private val images: List<EntryImage>
) {
    val size: Int = images.size
    
    fun forEach(iterator: (EntryImage) -> Unit) {
        images.forEach(iterator)
    }
}
