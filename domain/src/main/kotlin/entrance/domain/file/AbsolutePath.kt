package entrance.domain.file

import java.nio.file.Path
import java.nio.file.Paths

/**
 * 絶対パスを表すクラス.
 * 
 * @param path 絶対パス
 * @throws IllegalArgumentException パスが絶対パスでない場合
 */
data class AbsolutePath (
    private val path: Path
) {
    init {
        if (path.isAbsolute.not()) {
            throw IllegalArgumentException("指定されたパスは絶対パスではありません > $path")
        }
    }

    /**
     * 文字列形式のパスでインスタンスを生成する.
     * 
     * @param stringPath 文字列の絶対パス
     */
    constructor(stringPath: String): this(Paths.get(stringPath))

    /**
     * この絶対パスの文字列表現を取得する.
     * 
     * @return 絶対パスの文字列表現
     */
    fun stringPath() = path.toString()
}
