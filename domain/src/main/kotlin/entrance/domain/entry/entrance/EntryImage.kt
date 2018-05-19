package entrance.domain.entry.entrance

import entrance.domain.base.file.LocalFile
import java.awt.image.BufferedImage
import java.net.URI
import javax.imageio.ImageIO


class EntryImage (
    val localFile: LocalFile
) {
    val uri: URI = localFile.uri
    
    fun delete() {
        localFile.delete()
    }
    
    fun readBufferedImage(): BufferedImage {
        return ImageIO.read(localFile.javaFile)
    }

    fun moveTo(outputFile: LocalFile) {
        localFile.moveTo(outputFile)
    }
}
