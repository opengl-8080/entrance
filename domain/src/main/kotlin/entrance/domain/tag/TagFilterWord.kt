package entrance.domain.tag

import entrance.domain.util.error.ErrorMessage

data class TagFilterWord(
    val value: String
) {
    companion object {
        fun validate(value: String): ErrorMessage? {
            if (value.isEmpty()) {
                return ErrorMessage("絞り込みワードは必須です")
            }
            if (200 < value.codePointCount(0, value.length)) {
                return ErrorMessage("絞り込みワードは 200 文字以下で入力してください")
            }
            
            return null
        }
    }
    
    init {
        validate(value)?.throwAsInvalidValueException()
    }
    
    fun matches(token: String): Boolean = value.toUpperCase().contains(token.toUpperCase())
}
