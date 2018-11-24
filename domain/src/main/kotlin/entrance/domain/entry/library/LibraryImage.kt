package entrance.domain.entry.library

import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.image.ImageFile
import entrance.domain.entry.RegisteredDateTime
import entrance.domain.util.image.ImageResizer
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO
import kotlin.math.log


class LibraryImage(
    val localFile: LocalFile,
    val relativePath: RelativePath
) {
    private val logger = LoggerFactory.getLogger(LibraryImage::class.java)

    /**画像サイズが大きすぎるかどうかの閾値*/
    private val tooLargeImageSize = 10000 * 10000
    val registeredDateTime: RegisteredDateTime = RegisteredDateTime.now()
    val width: Int
    val height: Int
    private val tooLarge: Boolean
    
    init {
        logger.debug("ImageIO.read()")
        val image = ImageIO.read(localFile.javaFile)
        width = image.width
        height = image.height
        logger.debug("width={}, height={}", width, height)
        tooLarge = tooLargeImageSize < width * height
    }
    
    fun replaceToSmallImageIfTooLarge(): LibraryImage {
        if (!tooLarge) {
            logger.debug("not too large")
            return this
        }
        
        logger.debug("too large. create small image.")
        return createSmallSizeImage()
    }
    
    private fun createSmallSizeImage(): LibraryImage {
        val originalSizeImageFile = ImageFile.originalSizeImageFile(localFile)
        val smallSizeImageFile = localFile.parentDir.resolveFile(RelativePath("${localFile.baseName}.jpg"))

        ImageResizer.resize(localFile.javaPath, smallSizeImageFile.javaPath, 2000.0, BufferedImage.SCALE_SMOOTH)

        Files.move(localFile.javaPath, originalSizeImageFile.javaPath, StandardCopyOption.ATOMIC_MOVE)

        return LibraryImage(localFile = smallSizeImageFile, relativePath = relativePath.withFileName(smallSizeImageFile.name))
    }

    fun createThumbnail() {
        val thumbnailImageFile = ImageFile.thumbnailImageFile(localFile)
        ImageResizer.resize(localFile.javaPath, thumbnailImageFile.javaPath, 200.0, BufferedImage.SCALE_FAST)
    }
}