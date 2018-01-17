package entrance.domain.tag

import entrance.domain.InvalidValueException

data class TagFilterWord(
    val value: String
) {
    init {
        if (value.isEmpty()) {
            throw RuntimeException("絞り込みワードは空で登録できません")
        }
        if (200 < value.codePointCount(0, value.length)) {
            throw InvalidValueException("絞り込みワードは 200 文字以下で入力してください")
        }
    }
}