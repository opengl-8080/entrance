package entrance.domain.viewer.book

import entrance.domain.base.file.LocalFile
import entrance.domain.image.BaseImageFile

class BookImage(localFile: LocalFile): BaseImageFile(localFile) {
    override val statusText: String
        get() = "TODO (book の名前を表示させたい)"
}