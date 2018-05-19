package entrance.domain.util.file

import java.nio.file.Path
import java.nio.file.Paths

/**
 * 相対パスを表すクラス.
 * 
 * @param path 対象のパス
 * @throws IllegalArgumentException 指定したパスが相対パスでない場合
 */
data class DeprecatedRelativePath (
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
    fun asString() = path.toString().replace("\\", "/")

    /**
     * ファイル名だけを置き換えた新しい相対パスを生成する.
     * 
     * @param newFileName 新しいファイル名
     * @return ファイル名が置き換えられた新しい相対パス
     */
    fun replaceFileName(newFileName: String): DeprecatedRelativePath {
        return DeprecatedRelativePath(path.parent.resolve(newFileName))
    }
}