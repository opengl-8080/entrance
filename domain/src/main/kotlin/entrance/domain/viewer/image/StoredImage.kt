package entrance.domain.viewer.image

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath
import entrance.domain.image.BaseImageFile
import entrance.domain.sort.BaseSortableImage
import entrance.domain.sort.SortableItem
import entrance.domain.tag.Tag
import java.nio.file.Files
import java.nio.file.Paths

class StoredImage(
    val itemId: ItemId,
    val relativePath: RelativePath,
    val width: Int,
    val height: Int,
    localFile: LocalFile,
    tags: List<Tag>,
    rank: Rank
): BaseImageFile(localFile), SortableItem by BaseSortableImage(tags, rank) {

    override val statusText: String = "$width x $height (${localFile.name})"
    
    fun deleteLocalFile() {
        Files.delete(localFile.javaPath)
        Files.delete(Paths.get(thumbnailUri))
        val originalImagePath = Paths.get(originalSizeImageUri)
        if (Files.exists(originalImagePath)) {
            Files.delete(originalImagePath)
        }
    }
}