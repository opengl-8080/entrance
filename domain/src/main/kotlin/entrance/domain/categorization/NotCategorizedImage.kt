package entrance.domain.categorization

import entrance.domain.RegisteredDateTime
import entrance.domain.file.RelativePath

class NotCategorizedImage (
    private val path: RelativePath,
    val registeredDateTime: RegisteredDateTime
) {

    fun stringPath() = this.path.stringPath()
}