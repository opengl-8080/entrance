package entrance.domain.categorization

import entrance.domain.tag.Tag

/**
 * 複数の画像に共通で割り当て垂れていたタグのセット.
 */
data class CommonAssignedTagSet(
    private val tagSet: Set<Tag>
) {

    /**
     * 指定したタグがこのセットの中に含まれるか確認する.
     * 
     * @return タグが含まれる場合は true
     */
    fun contains(tag: Tag): Boolean = tagSet.contains(tag)

    /**
     * 選択を解除されたタグだけを抽出する.
     * 
     * @param selectedTagSet 選択されたタグのセット
     * @return 選択を解除されたタグのセット
     */
    fun filterReleasedTagSet(selectedTagSet: Set<Tag>): ReleasedTagSet {
        return ReleasedTagSet(tagSet - selectedTagSet)
    }

    /**
     * 選択されたタグセットの中にしか存在しないタグを抽出する.
     * 
     * ここで得たタグのセットは、画像に対して新規に割り当てるタグの候補となります.
     * 
     * ただし、共通で設定されていなかっただけで、既に画像に同じタグが割り当てられている可能性は存在します.
     * 実際にタグを割り当てる前に、その画像に既にタグが割り当てられていないか検証してから割り当てを保存してください.
     * 
     * @param selectedTagSet 選択されたタグのセット
     * @return 選択されたタグセットにしか存在しないタグ
     */
    fun filterNewSelectedTagSet(selectedTagSet: Set<Tag>): NewSelectedTagSet = NewSelectedTagSet(selectedTagSet - tagSet)
}
