package entrance.domain.image

import entrance.domain.ImageFile
import entrance.domain.file.LocalFile

class Image (
    val localFile: LocalFile
): ImageFile {

    override val stringPath: String
        get() = localFile.uriString
}