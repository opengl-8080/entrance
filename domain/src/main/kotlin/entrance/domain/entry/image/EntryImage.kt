package entrance.domain.entry.image

import entrance.domain.image.BaseImageFile
import entrance.domain.util.file.LocalFile
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO

/**
 * エントリディレクトリに存在するエントリ対象の画像ファイル.
 * 
 * @param localFile エントリ画像の指すローカルファイルオブジェクト.
 */
class EntryImage (
    localFile: LocalFile
): BaseImageFile(localFile) {
    
    /**
     * この画像ファイルの拡張子.
     */
    val extension = localFile.extension

    /**
     * このエントリ画像の [BufferedImage] を取得する.
     */
    fun readImage(): BufferedImage {
        return ImageIO.read(localFile.path.toFile())
    }

    /**
     * このエントリ画像を指定したローカルファイルに移動する.
     */
    fun moveTo(distFile: LocalFile) {
        Files.move(localFile.path, distFile.path, StandardCopyOption.ATOMIC_MOVE)
    }

    /**
     * このエントリ画像を削除する.
     */
    fun delete() {
        Files.delete(localFile.path)
    }
    
    override fun toString(): String {
        return "EntryImage(path=${localFile.path}, extension='$extension')"
    }
}