package entrance.domain.entry

import entrance.domain.util.file.RelativePath

/**
 * エントリが完了した画像.
 * 
 * @param path 画像ファイルの相対パス
 */
class EnteredImage(
    val path: RelativePath
) {
    /**
     * この画像のパスの文字列表現.
     */
    val stringPath = path.stringPath()

    /**
     * エントリが完了した日時.
     */
    val registeredDateTime = RegisteredDateTime.now().value

    override fun toString(): String {
        return "EnteredImage(stringPath='$stringPath', registeredDateTime=$registeredDateTime)"
    }
}
