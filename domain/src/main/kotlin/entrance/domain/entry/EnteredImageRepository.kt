package entrance.domain.entry

/**
 * エントリが完了した画像の情報を永続化するリポジトリ.
 */
interface EnteredImageRepository {

    /**
     * エントリが完了した画像の情報を永続化する.
     * 
     * @param enteredImage エントリが完了した画像
     */
    fun save(enteredImage: EnteredImage)
}