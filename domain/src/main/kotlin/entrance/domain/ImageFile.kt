package entrance.domain

import java.net.URI

interface ImageFile {
    val uri: URI
    val thumbnailUri: URI
    val originalSizeImageUri: URI
}
