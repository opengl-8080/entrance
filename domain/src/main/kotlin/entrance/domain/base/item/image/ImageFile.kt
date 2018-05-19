package entrance.domain.base.item.image

import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.Thumbnail
import java.net.URI
import java.nio.file.Path

class ImageFile (
    val relativePath: RelativePath,
    val localFile: LocalFile,
    val thumbnail: Thumbnail
) {
    val uri: URI = localFile.uri
    
    companion object {
        private val extensions = listOf("JPG", "JPEG", "GIF", "PNG")
        
        fun isImageFile(file: LocalFile): Boolean {
            return file.extension.toUpperCase() in extensions
        }
        
        fun originalSizeImageFile(file: LocalFile): LocalFile {
            val newName = "${file.baseName}_org.${file.extension}"
            return file.parentDir.resolveFile(RelativePath(newName))
        }
        
        fun thumbnailImageFile(file: LocalFile): LocalFile {
            val newName = "${file.baseName}_thumb.jpg"
            return file.parentDir.resolveFile(RelativePath(newName))
        }
    }
}