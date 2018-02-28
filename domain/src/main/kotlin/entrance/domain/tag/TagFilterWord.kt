package entrance.domain.tag

import entrance.domain.error.ErrorMessage
import entrance.domain.error.ErrorType
import entrance.domain.error.InvalidValueException

data class TagFilterWord(
    val value: String
) {
    companion object {
        val ERROR: ErrorType = object: ErrorType {}
        
        fun validate(value: String): ErrorMessage? {
            if (value.isEmpty()) {
                return ErrorMessage(ERROR, "絞り込みワードは必須です")
            }
            if (200 < value.codePointCount(0, value.length)) {
                return ErrorMessage(ERROR, "絞り込みワードは 200 文字以下で入力してください")
            }
            
            return null
        }
    }
    
    init {
        validate(value)?.also { throw InvalidValueException(it) }
    }
}
