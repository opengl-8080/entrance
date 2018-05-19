package entrance.domain.entry.library

/**
 * エントリが完了した画像の情報を永続化するリポジトリ.
 */
interface LibraryImageRepository {

    /**
     * エントリが完了した画像の情報を永続化する.
     * 
     * @param libraryImage エントリが完了した画像
     */
    fun save(libraryImage: LibraryImage)
}