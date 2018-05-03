package entrance.domain.entry

import entrance.domain.BaseImageFile
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
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
        val originalPath = localFile.path
        val originalSizePath = Paths.get(originalSizeImageUri)
        val smallSizePath = localFile.path.parent.resolve("${localFile.baseName}.jpg")

        resizeImage(originalPath, smallSizePath, 2000.0, BufferedImage.SCALE_SMOOTH)
        
        Files.move(originalPath, originalSizePath, StandardCopyOption.ATOMIC_MOVE)
        
        return EnteredImage(localFile=LocalFile(smallSizePath), path = this.path.replaceFileName(smallSizePath.fileName.toString()))
    }

    /**
     * この画像のサムネイル画像を生成する.
     */
    fun createThumbnail() {
        resizeImage(localFile.path, Paths.get(thumbnailUri), 200.0, BufferedImage.SCALE_FAST)
    }
    
    private fun resizeImage(original: Path, dist: Path, limitSize: Double, hint: Int) {
        val originalImage = ImageIO.read(original.toFile())
        val originalWidth = originalImage.width
        val originalHeight = originalImage.height

        val distWidth: Double
        val distHeight: Double
        
        if (originalWidth < originalHeight) {
            distWidth = originalWidth * (limitSize / originalHeight)
            distHeight = limitSize
        } else {
            distWidth = limitSize
            distHeight = originalHeight * (limitSize / originalWidth)
        }

        val distImage = BufferedImage(distWidth.toInt(), distHeight.toInt(), BufferedImage.TYPE_INT_RGB)
        val graphics = distImage.createGraphics()
        
        val resizedImage = originalImage.getScaledInstance(distWidth.toInt(), distHeight.toInt(), hint)
        graphics.drawImage(resizedImage, 0, 0, null)

        ImageIO.write(distImage, "jpg", dist.toFile())
    }

    override fun toString(): String {
        return "EnteredImage(path=$path, registeredDateTime=$registeredDateTime, relativeStringPath='$relativeStringPath')"
    }
}
