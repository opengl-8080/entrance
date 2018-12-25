package entrance.domain.image

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.LocalFile
import java.net.URI
import java.nio.file.Path
import java.util.regex.Pattern

abstract class BaseImageFile(
    protected val localFile: LocalFile
): ImageFile {
    private val fileNamePattern = Pattern.compile("""^(?<baseName>.*)\.(?<extension>[^.]+)$""")

    override val uri: URI = localFile.javaPath.toUri()

    override val javaPath: Path = localFile.javaPath

    override val parentDir: LocalDirectory = localFile.parentDir
    
    override val thumbnailUri: URI = localFile.let {
        val fileName = it.javaPath.fileName.toString()

        val matcher = fileNamePattern.matcher(fileName)
        matcher.find()
        val baseName = matcher.group("baseName")
        val thumbnailFileName = "${baseName}_thumb.jpg"

        it.javaPath.parent.resolve(thumbnailFileName).toUri()
    }

    override val originalSizeImageUri: URI = localFile.let {
        val fileName = it.javaPath.fileName.toString()

        val matcher = fileNamePattern.matcher(fileName)
        matcher.find()
        val baseName = matcher.group("baseName")
        val extension = matcher.group("extension")
        val originalSizeImageName = "${baseName}_org.$extension"

        it.javaPath.parent.resolve(originalSizeImageName).toUri()
    }
}