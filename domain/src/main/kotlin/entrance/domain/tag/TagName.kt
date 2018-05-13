package entrance.domain.tag

import entrance.domain.util.error.ErrorMessage

data class TagName(
    val value: String
): Comparable<TagName> {

    companion object {
        fun validate(value: String): ErrorMessage? {
            if (value.isEmpty()) {
                return ErrorMessage("タグ名は必須です")
            }
            if (100 < value.codePointCount(0, value.length)) {
                return ErrorMessage("タグ名は 100 文字以下で入力してください")
            }

            return null
        }
    }

    init {
        validate(value)?.throwAsInvalidValueException()
    }
    
    fun matches(token: String): Boolean = value.toUpperCase().contentEquals(token.toUpperCase())
    
    override fun compareTo(other: TagName): Int {
        return value.compareTo(other.value)
    }
}
