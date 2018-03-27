package entrance.domain.entry

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
     * 現在エントリディレクトリに存在する取り込み対象の画像を全て取得する.
     */
    fun getAllEntryImages(): AllEntryImages
}
