package entrance.domain.entry

import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
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
     * エントリディレクトリから相対パスを指定してファイルオブジェクトを取得する.
     * 
     * @param relativePath エントリディレクトリからの相対パス
     * @return 解決したファイルオブジェクト
     */
    fun resolveFile(relativePath: RelativePath): LocalFile
}
