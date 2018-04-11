package entrance.domain.image

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.file.LocalFile
import entrance.domain.file.RelativePath
import java.nio.file.Files

class Image (
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