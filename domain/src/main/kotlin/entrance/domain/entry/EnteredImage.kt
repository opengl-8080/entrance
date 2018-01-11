package entrance.domain.entry

import entrance.domain.RegisteredDateTime
import entrance.domain.file.RelativePath

class EnteredImage(
    path: RelativePath
) {
    val stringPath = path.stringPath()
    val notCategorized = true
    val registeredDateTime = RegisteredDateTime.now().value
}
