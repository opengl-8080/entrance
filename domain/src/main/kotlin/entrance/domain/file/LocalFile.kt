package entrance.domain.file

import java.nio.file.Path


class LocalFile (
    val path: Path
) {
    
    val extension: String = path.fileName.toString().substringAfterLast(".")
    
    fun isImage() = listOf("jpg", "jpeg", "png", "gif", "bmp").any { it.equals(extension, ignoreCase = true) }
}