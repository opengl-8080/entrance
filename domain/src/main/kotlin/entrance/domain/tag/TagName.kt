package entrance.domain.tag

import entrance.domain.error.ErrorMessage
import entrance.domain.error.ErrorType
import entrance.domain.error.InvalidValueException

data class TagName(
    val value: String
) {
    companion object {
        val ERROR = object : ErrorType {}

        fun validate(value: String): ErrorMessage? {
            if (value.isEmpty()) {
                return ErrorMessage(ERROR, "タグ名は必須です")
            }
            if (100 < value.codePointCount(0, value.length)) {
                return ErrorMessage(ERROR, "タグ名は 100 文字以下で入力してください")
            }

            return null
        }
    }

    init {
        validate(value)?.also { throw InvalidValueException(it) }
    }
}
