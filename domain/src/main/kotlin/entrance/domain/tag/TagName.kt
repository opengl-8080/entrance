package entrance.domain.tag

import entrance.domain.error.ErrorCode
import entrance.domain.error.InvalidValueException

data class TagName(
    val value: String
) {
    companion object {
        fun validate(value: String) {
            if (value.isEmpty()) {
                throw InvalidValueException(TagNameError.REQUIRED)
            }
            if (100 < value.codePointCount(0, value.length)) {
                throw InvalidValueException(TagNameError.TOO_LONG)
            }
        }
    }
    
    init {
        validate(value)
    }
}

enum class TagNameError(private val errorMessage: String): ErrorCode {
    REQUIRED("タグ名は必須です"),
    TOO_LONG("タグ名は 100 文字以下で入力してください")
    ;

    override fun getMessage(): String = this.errorMessage
}