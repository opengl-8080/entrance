package entrance.domain.entry.book


class AllEntryBooks (
    val books: List<EntryBook>
) {
    
    val size: Long = books.size.toLong()
    
    /**
     * 各エントリ画像に対して反復処理を行う.
     *
     * @param iterator 反復処理
     */
    fun forEachBooks(iterator: (EntryBook) -> Unit) = books.forEach(iterator)
}