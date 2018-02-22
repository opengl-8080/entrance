package entrance.domain.tag

import entrance.domain.InvalidValueException

data class TagName(
    val value: String
) {
    init {
        if (value.isEmpty()) {
            throw InvalidValueException("タグ名は必須です")
        }
        if (100 < value.codePointCount(0, value.length)) {
            throw InvalidValueException("タグ名は 100 文字以下で入力してください")
        }
    }
}