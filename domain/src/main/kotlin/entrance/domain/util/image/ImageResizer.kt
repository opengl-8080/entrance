package entrance.domain.util.image

import java.awt.image.BufferedImage
import java.nio.file.Path
import javax.imageio.ImageIO

object ImageResizer {

    fun resize(original: Path, dist: Path, limitSize: Double, hint: Int) {
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
}