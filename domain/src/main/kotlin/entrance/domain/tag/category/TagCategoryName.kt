package entrance.domain.tag.category

import entrance.domain.util.error.ErrorMessage

data class TagCategoryName(
    val value: String
) {
    companion object {
        val OTHERS = TagCategoryName("その他")
        
        fun validate(value: String): ErrorMessage? {
            if (value.isEmpty()) {
                return ErrorMessage("タグカテゴリ名は必須です")
            }
            if (100 < value.codePointCount(0, value.length)) {
                return ErrorMessage("タグカテゴリ名は 100 文字以下で入力してください")
            }

            return null
        }
    }

    init {
        validate(value)?.throwAsInvalidValueException()
    }
    
    fun matches(text: String): Boolean {

        val upperCaseText = text.toUpperCase()
        val upperCaseName = value.toUpperCase()

        return upperCaseText
                .split(" ")
                .filter { it != "" }
                .all { token ->
                    upperCaseName.contains(token)
                }
    }
}