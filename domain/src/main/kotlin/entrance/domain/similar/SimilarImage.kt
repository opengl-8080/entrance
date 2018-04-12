package entrance.domain.similar

import entrance.domain.ImageFile
import entrance.domain.util.file.LocalFile


class SimilarImage(
    private val localFile: LocalFile
): ImageFile {

    override val stringPath: String
        get() = localFile.uriString
}