package entrance.domain.viewer

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.nio.file.Files

class StoredImage(
    val itemId: ItemId,
    relativePath: RelativePath,
    private val localFile: LocalFile
): ImageFile {
    
    val stringRelativePath: String = relativePath.stringPath()
    
    override val stringPath: String = localFile.uriString
    
    fun deleteLocalFile() {
        Files.delete(localFile.path)
    }
}