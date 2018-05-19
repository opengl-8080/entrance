package entrance.domain.base.file

import java.nio.file.Path
import java.nio.file.Paths

data class RelativePath (
    val javaPath: Path
) {
    
    constructor(path: String): this(Paths.get(path))
    
    init {
        if (javaPath.isAbsolute) {
            throw IllegalArgumentException("パスが相対パスではありません. javaPath=$javaPath")
        }
    }
    
    val value: String = javaPath.toString().replace("\\", "/")

    /**
     * ファイル名だけを指定したものに置き換えた新しい相対パスを取得する.
     *
     * @param name 置き換え後のファイル名
     * @return ファイル名が置き換えられた新しいパスオブジェクト
     */
    fun withFileName(name: String): RelativePath {
        return RelativePath(javaPath.parent.resolve(name))
    }
}