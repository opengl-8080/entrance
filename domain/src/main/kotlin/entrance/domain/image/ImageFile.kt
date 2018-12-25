package entrance.domain.image

import entrance.domain.ThumbnailImage
import entrance.domain.base.file.LocalDirectory
import java.net.URI
import java.nio.file.Path

interface ImageFile: ThumbnailImage {
    /**
     * このファイルの URI.
     */
    val uri: URI

    /**
     * このファイルの [java.nio.file.Path].
     */
    val javaPath: Path

    /**
     * このファイルが存在するディレクトリ.
     */
    val parentDir: LocalDirectory

    /**
     * このファイルが縮小されている場合の、オリジナルの画像の URI.
     */
    val originalSizeImageUri: URI
}
