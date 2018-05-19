package entrance.domain.entry.entrance


data class EntryBooks (
    private val books: List<EntryBook>
) {
    
    val size: Int = books.size
    
    fun forEach(iterator: (EntryBook) -> Unit) = books.forEach(iterator)
}