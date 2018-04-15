package entrance.domain

import entrance.domain.util.file.LocalFile
import java.net.URI
import java.util.regex.Pattern

abstract class BaseImageFile(
    protected val localFile: LocalFile
): ImageFile {
    private val fileNamePattern = Pattern.compile("""^(?<baseName>.*)\.(?<extension>[^.]+)$""")

    override val uri: URI = localFile.path.toUri()
    
    override val thumbnailUri: URI = localFile.let {
        val fileName = it.path.fileName.toString()

        val matcher = fileNamePattern.matcher(fileName)
        matcher.find()
        val baseName = matcher.group("baseName")
        val extension = matcher.group("extension")
        val thumbnailFileName = "${baseName}_thumb.$extension"

        it.path.parent.resolve(thumbnailFileName).toUri()
    }
}