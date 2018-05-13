package entrance.domain.viewer.image

import entrance.domain.image.BaseImageFile
import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.sort.BaseSortableImage
import entrance.domain.sort.SortableItem
import entrance.domain.tag.Tag
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.nio.file.Files
import java.nio.file.Paths

class StoredImage(
    val itemId: ItemId,
    val relativePath: RelativePath,
    localFile: LocalFile,
    tags: List<Tag>,
    rank: Rank
): BaseImageFile(localFile), SortableItem by BaseSortableImage(tags, rank) {
    
    fun deleteLocalFile() {
        Files.delete(localFile.path)
        Files.delete(Paths.get(thumbnailUri))
        val originalImagePath = Paths.get(originalSizeImageUri)
        if (Files.exists(originalImagePath)) {
            Files.delete(originalImagePath)
        }
    }
}