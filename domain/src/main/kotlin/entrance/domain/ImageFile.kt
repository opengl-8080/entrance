package entrance.domain

data class ImageFile(
    private val path: RelativePath
) {
    fun stringPath() = path.stringPath()
}
