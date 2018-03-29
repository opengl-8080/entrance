package entrance.domain.categorization

import entrance.domain.RegisteredDateTime
import entrance.domain.file.LocalFile

/**
 * 未分類画像
 */
class NotCategorizedImage (
    file: LocalFile,
    val registeredDateTime: RegisteredDateTime
) {

    val uriString: String = file.uriString
}