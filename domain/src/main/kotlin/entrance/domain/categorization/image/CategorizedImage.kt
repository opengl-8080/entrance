package entrance.domain.categorization.image

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.categorization.CategorizedItem
import entrance.domain.image.BaseImageFile
import entrance.domain.image.ImageFile
import entrance.domain.tag.Tag
import entrance.domain.util.file.LocalFile

class CategorizedImage(
    val itemId: ItemId,
    localFile: LocalFile,
    tagSet: Set<Tag>,
    rank: Rank
): CategorizedItem(tagSet, rank), ImageFile by object: BaseImageFile(localFile) {
    
}
