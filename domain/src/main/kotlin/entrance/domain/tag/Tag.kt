package entrance.domain.tag

import entrance.domain.tag.category.TagCategory

/**
 * 変更不可の参照のタグオブジェクト.
 * 
 * このオブジェクトは不変で、参照のためだけに使用する場合に使用することを意図しています.
 * 
 * @param id タグのID
 * @param name タグの名前
 * @param filterWord タグの絞り込みキーワード
 */
class Tag (
    val id: TagId,
    val name: TagName,
    val filterWord: TagFilterWord,
    val tagCategory: TagCategory
) {

    /**
     * 絞り込み条件として指定された入力テキストが、このタグにマッチするか確認する.
     * 
     * @param text 絞り込み条件として指定されたテキスト
     * @return マッチする場合は true
     */
    fun matches(text: String): Boolean {
        val upperCaseText = text.toUpperCase()
        val upperCaseName = name.value.toUpperCase()
        val upperCaseFilterWord = filterWord.value.toUpperCase()
        
        return upperCaseText
                .split(" ")
                .filter { it != "" }
                .all { token ->
                    upperCaseName.contains(token) || upperCaseFilterWord.contains(token)
                }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
