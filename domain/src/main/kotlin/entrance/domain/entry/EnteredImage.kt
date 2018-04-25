package entrance.domain.entry

import entrance.domain.BaseImageFile
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO

/**
 * エントリが完了した画像.
 * 
 * @param path 画像ファイルの相対パス
 */
class EnteredImage(
    localFile: LocalFile,
    val path: RelativePath
): BaseImageFile(localFile) {
    /**
     * エントリが完了した日時.
     */
    val registeredDateTime = RegisteredDateTime.now().value
    
    val relativeStringPath: String = path.stringPath()

    /**画像サイズが大きすぎるかどうかの閾値*/
    private val tooLargeImageSize = 10000 * 10000

    /**
     * この画像のサイズが大きすぎるかどうかを確認する.
     *
     * 画像が大きい場合、画像のプレビューなどが重くなるので、アプリケーションの速度を優先して画像を小さくするなどの対処が必要になります.
     *
     * このメソッドは、この画像がその対処をすべきかどうかの基準となる大きさを超えているかどうかに使用します.
     *
     * @return この画像が大きすぎる場合は true
     */
    fun isTooLarge(): Boolean {
        val image = ImageIO.read(localFile.path.toFile())
        val width = image.width
        val height = image.height
        return tooLargeImageSize < width * height
    }

    /**
     * この画像を小さくした画像を生成する.
     */
    fun createSmallSizeImage(): EnteredImage {
        val originalSizeImagePath = localFile.path
        val originalImage = ImageIO.read(originalSizeImagePath.toFile())
        val originalWidth = originalImage.width
        val originalHeight = originalImage.height

        val (width, height) = if (originalWidth < originalHeight) {
            val height = 2000.0
            val width = originalWidth * (height / originalHeight)
            width to height
        } else {
            val width = 2000.0
            val height = originalHeight * (width / originalWidth)
            width to height
        }

        val scaledImage = originalImage.getScaledInstance(width.toInt(), height.toInt(), BufferedImage.SCALE_SMOOTH)

        val smallSizeImage = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        val graphics = smallSizeImage.createGraphics()
        graphics.drawImage(scaledImage, 0, 0, null)

        val originalSizeImageFile = Paths.get(originalSizeImageUri)
        val smallSizeImageFile = localFile.path.parent.resolve("${localFile.baseName}.jpg")
        Files.move(originalSizeImagePath, originalSizeImageFile, StandardCopyOption.ATOMIC_MOVE)
        ImageIO.write(smallSizeImage, "jpg", smallSizeImageFile.toFile())
        
        return EnteredImage(localFile=LocalFile(smallSizeImageFile), path = this.path.replaceFileName(smallSizeImageFile.fileName.toString()))
    }

    /**
     * この画像のサムネイル画像を生成する.
     */
    fun createThumbnail() {
        val originalImage = ImageIO.read(localFile.path.toFile())
        val originalWidth = originalImage.width
        val originalHeight = originalImage.height
        
        val (width, height) = if (originalWidth < originalHeight) {
            val height = 200.0
            val width = originalWidth * (height / originalHeight)
            width to height
        } else {
            val width = 200.0
            val height = originalHeight * (width / originalWidth)
            width to height
        }

        val scaledImage = originalImage.getScaledInstance(width.toInt(), height.toInt(), BufferedImage.SCALE_FAST)

        val thumbnailImage = BufferedImage(width.toInt(), height.toInt(), BufferedImage.TYPE_INT_RGB)
        val graphics = thumbnailImage.createGraphics()
        graphics.drawImage(scaledImage, 0, 0, null)

        ImageIO.write(thumbnailImage, "jpg", Paths.get(thumbnailUri).toFile())
    }

    override fun toString(): String {
        return "EnteredImage(path=$path, registeredDateTime=$registeredDateTime, relativeStringPath='$relativeStringPath')"
    }
}
