package entrance.domain.file

import java.nio.file.Path
import java.nio.file.Paths


data class RelativePath (
    private val path: Path
) {
    init {
        if (path.isAbsolute) {
            throw IllegalArgumentException("パスが相対パスではありません > $path")
        }
    }
    
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