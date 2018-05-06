package entrance.domain.image

import entrance.domain.ThumbnailImage
import java.net.URI

interface ImageFile: ThumbnailImage {
    val uri: URI
    val originalSizeImageUri: URI
}
