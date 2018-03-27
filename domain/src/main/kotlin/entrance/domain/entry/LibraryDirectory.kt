package entrance.domain.entry

/**
 * 取り込まれたファイルを保管するディレクトリ.
 */
interface LibraryDirectory {

    /**
     * 指定したエントリ画像を所定のディレクトリに保存する.
     * 
     * @param entryImage 保存するエントリ画像
     * @return 保存された画像ファイル
     */
    fun move(entryImage: EntryImage): EnteredImage
}