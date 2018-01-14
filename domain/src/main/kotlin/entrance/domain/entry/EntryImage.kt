package entrance.domain.entry

import entrance.domain.file.LocalFile

/**
 * エントリディレクトリに存在するエントリ対象の画像ファイル.
 */
class EntryImage (
    file: LocalFile
) {
    val path = file.path
    val extension = file.extension
    
    override fun toString(): String {
        return "EntryImage(path=$path, extension='$extension')"
    }
}