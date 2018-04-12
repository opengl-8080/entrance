package entrance.domain.entry

import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath

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

    /**
     * ホームからの相対パスでファイルを解決する.
     */
    fun resolveFile(relativePath: RelativePath): LocalFile
}