package entrance.domain.management

import entrance.domain.ImageFile
import entrance.domain.file.LocalFile
import entrance.domain.tag.Tag


class ManagedImage(
    localFile: LocalFile,
    val tagList: List<Tag>
): ImageFile {

    override val stringPath = localFile.uriString
}