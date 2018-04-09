package entrance.domain.entry

import entrance.domain.file.LocalFile
import entrance.domain.file.RelativePath
import java.nio.file.Path

/**
 * エントリディレクトリ.
 * 
 * このディレクトリは entrance により監視されています.
 * 取り込み対象となるファイルがディレクトリに配置されると、
 * ファイルは自動的に entrance に取り込まれます.
 */
interface EntryDirectory {

    /**
     * このディレクトリのパスを取得する
     * @return ディレクトリのパス
     */
    fun path(): Path

    /**
     * エントリディレクトリから相対パスを指定してエントリ画像を取得する.
     * 
     * @param relativePath エントリ画像の相対パス
     * @return 解決したエントリ画像オブジェクト
     */
    fun resolveFile(relativePath: RelativePath): EntryImage
}
