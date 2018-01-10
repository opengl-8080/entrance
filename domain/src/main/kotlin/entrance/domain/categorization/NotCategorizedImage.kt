package entrance.domain.categorization

import entrance.domain.RegisteredDateTime
import entrance.domain.entry.SavedImage
import entrance.domain.file.RelativePath

class NotCategorizedImage (
    private val path: RelativePath,
    val registeredDateTime: RegisteredDateTime
) {
    
    companion object {
        
        fun convert(savedImage: SavedImage) = NotCategorizedImage(
            path = savedImage.path,
            registeredDateTime = RegisteredDateTime.now()
        )
    }

    fun stringPath() = this.path.stringPath()
}