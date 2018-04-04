package entrance.domain.categorization

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.file.LocalFile

open class NotTaggedImage(
    val itemId: ItemId,
    localFile: LocalFile
): ImageFile {
    override val stringPath: String = localFile.uriString
}