package entrance.domain.categorization

import entrance.domain.ItemId
import entrance.domain.file.LocalFile
import entrance.domain.tag.Tag

class TaggedImage(
    itemId: ItemId,
    localFile: LocalFile,
    val tagList: List<Tag>
): NotTaggedImage(itemId, localFile)
