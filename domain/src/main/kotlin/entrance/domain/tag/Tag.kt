package entrance.domain.tag

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
