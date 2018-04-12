package entrance.domain.categorization

import entrance.domain.ImageFile
import entrance.domain.ItemId
import entrance.domain.util.file.LocalFile
import entrance.domain.tag.Tag

class TaggedImage(
    val itemId: ItemId,
    val localFile: LocalFile,
    val tagSet: Set<Tag>
): ImageFile {
    override val stringPath: String = localFile.uriString

    /**
     * 新規に選択されたタグセットの中から、この画像に既に割り当てられているタグを除外し、
     * 本当に割り当てられるべき新規タグのセットを取得する.
     * 
     * @param newSelectedTagSet 新規に選択されたタグのセット
     * @return この画像に既に割り当てられていたタグを除外した、新規割り当て対象のタグセット
     */
    fun filterNewAssignedTagSet(newSelectedTagSet: NewSelectedTagSet): NewAssignedTagSet {
        return NewAssignedTagSet(newSelectedTagSet.tagSet - tagSet)
    }
}
