package entrance.domain.wallpaper

import entrance.domain.base.file.LocalFile
import entrance.domain.image.BaseImageFile

class Wallpaper(localFile: LocalFile): BaseImageFile(localFile) {
    override val statusText: String = "TODO (必要？)"
}
