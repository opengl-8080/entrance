package entrance.domain.viewer

import entrance.domain.BaseImageFile
import entrance.domain.ItemId
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.nio.file.Files
import java.nio.file.Paths

class StoredImage(
    val itemId: ItemId,
    val relativePath: RelativePath,
    localFile: LocalFile
): BaseImageFile(localFile) {
    
    fun deleteLocalFile() {
        Files.delete(localFile.path)
        Files.delete(Paths.get(thumbnailUri))
        val originalImagePath = Paths.get(originalSizeImageUri)
        if (Files.exists(originalImagePath)) {
            Files.delete(originalImagePath)
        }
    }
}