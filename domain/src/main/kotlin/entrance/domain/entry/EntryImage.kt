package entrance.domain.entry

import entrance.domain.file.LocalFile

/**
 * エントリディレクトリに存在するエントリ対象の画像ファイル.
 * 
 * @param file エントリ画像の指すローカルファイルオブジェクト.
 */
class EntryImage (
    file: LocalFile
) {
    /**
     * この画像ファイルのパス.
     */
    val path = file.path

    /**
     * この画像ファイルの拡張子.
     */
    val extension = file.extension
    
    override fun toString(): String {
        return "EntryImage(path=$path, extension='$extension')"
    }
}