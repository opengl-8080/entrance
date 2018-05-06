package entrance.domain.entry.image

import entrance.domain.image.BaseImageFile
import entrance.domain.entry.RegisteredDateTime
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import entrance.domain.util.image.ImageResizer
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO

/**
 * エントリが完了した画像.
 * 
 * @param relativePath 画像ファイルの相対パス
 */
class EnteredImage(
    localFile: LocalFile,
    val relativePath: RelativePath
): BaseImageFile(localFile) {
    /**
     * エントリが完了した日時.
     */
    val registeredDateTime = RegisteredDateTime.now().value

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

        ImageResizer.resize(originalPath, smallSizePath, 2000.0, BufferedImage.SCALE_SMOOTH)
        
        Files.move(originalPath, originalSizePath, StandardCopyOption.ATOMIC_MOVE)
        
        return EnteredImage(localFile = LocalFile(smallSizePath), relativePath = this.relativePath.replaceFileName(smallSizePath.fileName.toString()))
    }

    /**
     * この画像のサムネイル画像を生成する.
     */
    fun createThumbnail() {
        ImageResizer.resize(localFile.path, Paths.get(thumbnailUri), 200.0, BufferedImage.SCALE_FAST)
    }

    override fun toString(): String {
        return "EnteredImage(path=$relativePath, registeredDateTime=$registeredDateTime)"
    }
}
