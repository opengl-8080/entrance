package entrance.domain.util.file

import java.nio.file.Path
import java.nio.file.Paths

/**
 * 相対パスを表すクラス.
 * 
 * @param path 対象のパス
 * @throws IllegalArgumentException 指定したパスが相対パスでない場合
 */
data class RelativePath (
    internal val path: Path
) {
    init {
        if (path.isAbsolute) {
            throw IllegalArgumentException("パスが相対パスではありません > $path")
        }
    }

    /**
     * 文字列表現のパスでインスタンスを生成する.
     * 
     * @param stringPath 文字列のパス
     */
    constructor(stringPath: String): this(Paths.get(stringPath))

    /**
     * 文字列形式の相対パスを取得する.
     * 
     * パス区切り文字は全て `/` に変換されます.
     * 
     * @return 文字列形式の相対パス
     */
    fun stringPath() = path.toString().replace("\\", "/")
}