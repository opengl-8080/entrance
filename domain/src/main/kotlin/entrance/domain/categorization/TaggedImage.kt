package entrance.domain.categorization

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.file.LocalFile
import entrance.domain.tag.Tag

class TaggedImage(
    val itemId: ItemId,
    val localFile: LocalFile,
    val tagList: List<Tag>
): ImageFile {
    override val stringPath: String = localFile.uriString
}
