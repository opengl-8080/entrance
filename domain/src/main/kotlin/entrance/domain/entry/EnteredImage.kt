package entrance.domain.entry

import entrance.domain.BaseImageFile
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.awt.image.BufferedImage
import java.nio.file.Paths
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
