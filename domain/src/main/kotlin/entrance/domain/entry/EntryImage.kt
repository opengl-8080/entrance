package entrance.domain.entry

import entrance.domain.BaseImageFile
import entrance.domain.util.file.LocalFile

/**
 * エントリディレクトリに存在するエントリ対象の画像ファイル.
 * 
 * @param localFile エントリ画像の指すローカルファイルオブジェクト.
 */
class EntryImage (
    localFile: LocalFile
): BaseImageFile(localFile) {
    /**
     * この画像ファイルのパス.
     */
    val path = localFile.path
    
    /**
     * この画像ファイルの拡張子.
     */
    val extension = localFile.extension
    
    override fun toString(): String {
        return "EntryImage(path=$path, extension='$extension')"
    }
}