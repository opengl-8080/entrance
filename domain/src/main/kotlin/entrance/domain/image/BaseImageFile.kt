package entrance.domain.image

import entrance.domain.base.file.LocalFile
import java.net.URI
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.regex.Pattern
import javax.imageio.ImageIO

abstract class BaseImageFile(
    protected val localFile: LocalFile
): ImageFile {
    private val fileNamePattern = Pattern.compile("""^(?<baseName>.*)\.(?<extension>[^.]+)$""")

    override val uri: URI = localFile.javaPath.toUri()
    
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
    
    private var _statusText: String? = null
    
    init {
        threadPool.submit {
            val fileName = Paths.get(uri).fileName.toString()
            val image = ImageIO.read(this.uri.toURL())
            val width = image.width
            val height = image.height

            synchronized(this) {
                _statusText = "$width x $height : $fileName"
            }
        }
    }

    override val statusText: String
    get() {
        synchronized(this) {
            return _statusText ?: "読み込み中..."
        }
    }
    
    companion object {
        private val threadPool = Runtime.getRuntime().let {
            Executors.newFixedThreadPool(if (it.availableProcessors() == 1) 1 else {it.availableProcessors() - 1})
        }
    }
}