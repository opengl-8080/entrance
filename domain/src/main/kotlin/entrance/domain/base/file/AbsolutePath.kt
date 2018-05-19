package entrance.domain.base.file

import java.nio.file.Path
import java.nio.file.Paths


data class AbsolutePath(
    val javaPath: Path
) {
    init {
        if (!javaPath.isAbsolute) {
            throw IllegalArgumentException("パスが絶対パスではありません javaPath=$javaPath")
        }
    }

    /**
     * このパスが指すファイルまたはディレクトリの名前.
     */
    val name: String = javaPath.fileName.toString()
    
    constructor(path: String): this(Paths.get(path))
    
    companion object {

        /**
         * 相対パスか絶対パスか定かでないパスから絶対パスを構築する.
         * 
         * パスが相対パスの場合は、現在のディレクトリを起点にした絶対パスに変換したうえでインスタンスを生成する.
         * 
         * @param path 相対か絶対か不明な、曖昧なパス
         * @return 絶対パス
         */
        fun fromAmbiguousPath(path: Path): AbsolutePath {
            return if (path.isAbsolute) {
                AbsolutePath(path)
            } else {
                AbsolutePath(path.toAbsolutePath())
            }
        }
    }
}
