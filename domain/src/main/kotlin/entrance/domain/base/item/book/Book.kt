package entrance.domain.base.item.book

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.LocalFile
import entrance.domain.base.file.RelativePath


class Book {
    
    companion object {
        fun thumbnailImageFile(directory: LocalDirectory): LocalFile {
            return directory.resolveFile(RelativePath("thumbnail.jpg"))
        }
    }
}