package entrance.domain.entry

/**
 * 全てのエントリ対象画像を保持するコレクション.
 */
data class AllEntryImages (
    private val images: List<EntryImage>
) {

    /**
     * このエントリ画像の全画像数.
     */
    val size: Int = images.size
    
    /**
     * 各エントリ画像に対して反復処理を行う.
     * 
     * @param iterator 反復処理
     */
    fun forEachImages(iterator: (EntryImage) -> Unit) = images.forEach(iterator) 
}