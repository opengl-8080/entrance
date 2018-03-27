package entrance.domain.tag

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
    val filterWord: TagFilterWord
) {

    /**
     * 絞り込み条件として指定された入力テキストが、このタグにマッチするか確認する.
     * 
     * @param text 絞り込み条件として指定されたテキスト
     * @return マッチする場合は true
     */
    fun matches(text: String): Boolean {
        val upperCaseText = text.toUpperCase()
        return name.value.toUpperCase().contains(upperCaseText)
                || filterWord.value.toUpperCase().contains(upperCaseText)
    }
}
