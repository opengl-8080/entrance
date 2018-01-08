package entrance.domain

import java.nio.file.Path
import java.nio.file.Paths

data class AbsolutePath (
    private val path: Path
) {
    init {
        if (path.isAbsolute.not()) {
            throw IllegalArgumentException("指定されたパスは絶対パスではありません > $path")
        }
    }
    
    constructor(stringPath: String): this(Paths.get(stringPath))

    fun stringPath() = path.toString()
}
