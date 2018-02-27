package entrance.domain.tag

import entrance.domain.error.ErrorCode
import entrance.domain.error.InvalidValueException

data class TagFilterWord(
    val value: String
) {
    companion object {
        fun validate(value: String) {
            if (value.isEmpty()) {
                throw InvalidValueException(TagFilterWordError.REQUIRED)
            }
            if (200 < value.codePointCount(0, value.length)) {
                throw InvalidValueException(TagFilterWordError.TOO_LONG)
            }
        }
    }
    
    init {
        validate(value)
    }
}

enum class TagFilterWordError(private val errorMessage: String): ErrorCode {
    REQUIRED("絞り込みワードは必須です"),
    TOO_LONG("絞り込みワードは 200 文字以下で入力してください"),
    ;

    override fun getMessage(): String = this.errorMessage
}