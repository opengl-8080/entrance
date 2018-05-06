package entrance.domain.entry

import entrance.domain.entry.book.EnteredBook
import entrance.domain.entry.book.EntryBook
import entrance.domain.entry.image.EnteredImage
import entrance.domain.entry.image.EntryImage
import entrance.domain.util.file.Directory
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
     * 指定したエントリブックを所定のディレクトリに保存する.
     * 
     * @param entryBook 保存するエントリブック
     * @return 保存されたブック
     */
    fun move(entryBook: EntryBook): EnteredBook

    /**
     * ホームからの相対パスでファイルを解決する.
     */
    fun resolveFile(relativePath: RelativePath): LocalFile

    /**
     * ホームからの相対パスでディレクトリを解決する.
     */
    fun resolveDirectory(relativePath: RelativePath): Directory
}