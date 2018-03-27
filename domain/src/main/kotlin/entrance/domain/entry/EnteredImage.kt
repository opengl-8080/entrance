package entrance.domain.entry

import entrance.domain.RegisteredDateTime
import entrance.domain.file.RelativePath

/**
 * エントリが完了した画像.
 * 
 * @param path 画像ファイルの相対パス
 */
class EnteredImage(
    path: RelativePath
) {
    /**
     * この画像のパスの文字列表現.
     */
    val stringPath = path.stringPath()

    /**
     * この画像が分類済みかどうか.
     */
    val notCategorized = true

    /**
     * エントリが完了した日時.
     */
    val registeredDateTime = RegisteredDateTime.now().value

    override fun toString(): String {
        return "EnteredImage(stringPath='$stringPath', notCategorized=$notCategorized, registeredDateTime=$registeredDateTime)"
    }
}
