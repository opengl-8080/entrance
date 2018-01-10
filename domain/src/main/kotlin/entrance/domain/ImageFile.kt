package entrance.domain

import entrance.domain.file.RelativePath

data class ImageFile(
    private val path: RelativePath
) {
    fun stringPath() = path.stringPath()
}
