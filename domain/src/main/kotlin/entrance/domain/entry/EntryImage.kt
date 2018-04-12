package entrance.domain.entry

import entrance.domain.ImageFile
import entrance.domain.util.file.LocalFile

/**
 * エントリディレクトリに存在するエントリ対象の画像ファイル.
 * 
 * @param file エントリ画像の指すローカルファイルオブジェクト.
 */
class EntryImage (
    private val file: LocalFile
): ImageFile {
    /**
     * この画像ファイルのパス.
     */
    val path = file.path
    
    /**
     * この画像ファイルの拡張子.
     */
    val extension = file.extension

    override val stringPath: String
        get() = file.uriString
    
    override fun toString(): String {
        return "EntryImage(path=$path, extension='$extension')"
    }
}