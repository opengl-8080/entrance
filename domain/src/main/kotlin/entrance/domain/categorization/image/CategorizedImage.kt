package entrance.domain.categorization.image

import entrance.domain.ItemId
import entrance.domain.Rank
import entrance.domain.base.file.LocalFile
import entrance.domain.categorization.CategorizedItem
import entrance.domain.image.BaseImageFile
import entrance.domain.image.ImageFile
import entrance.domain.tag.Tag

class CategorizedImage(
    val itemId: ItemId,
    localFile: LocalFile,
    tagSet: Set<Tag>,
    rank: Rank
): CategorizedItem(tagSet, rank), ImageFile by object: BaseImageFile(localFile) {

    override val statusText: String = "TODO (必要？)"
}
