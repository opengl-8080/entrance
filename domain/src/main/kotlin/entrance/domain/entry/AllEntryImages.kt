package entrance.domain.entry

data class AllEntryImages (
    private val images: List<EntryImage>
) {
    fun forEachImages(iterator: (EntryImage) -> Unit) = images.forEach(iterator) 
}