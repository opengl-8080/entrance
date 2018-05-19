package entrance.domain.entry.library

import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.image.ImageFile
import entrance.domain.entry.RegisteredDateTime
import entrance.domain.util.image.ImageResizer
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO


class LibraryImage(
    val localFile: LocalFile,
    val relativePath: RelativePath
) {
    
    val registeredDateTime: RegisteredDateTime = RegisteredDateTime.now()
    
    /**画像サイズが大きすぎるかどうかの閾値*/
    private val tooLargeImageSize = 10000 * 10000
    
    fun replaceToSmallImageIfTooLarge(): LibraryImage {
        if (!isTooLarge()) {
            return this
        }
        
        return createSmallSizeImage()
    }
    
    private fun isTooLarge(): Boolean {
        val image = ImageIO.read(localFile.javaFile)
        val width = image.width
        val height = image.height
        return tooLargeImageSize < width * height
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