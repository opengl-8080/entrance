package entrance.view.javafx.control

import entrance.domain.tag.Tag
import javafx.scene.control.ToggleButton

/**
 * 選択可能なタグのビュー.
 */
class TagView(val tag: Tag): ToggleButton(tag.name.value) {
    init {
        managedProperty().bind(visibleProperty())
        
        styleClass.add("tag")
        
        selectedProperty().addListener {_,_, selected ->
            if (selected) {
                styleClass += "tag--selected"
            } else {
                styleClass -= "tag--selected"
            }
        }
    }

    /**
     * このタグビューが、指定したタグと同じタグを持つか確認する.
     * 
     * @return 指定したタグと同じ場合は true
     */
    fun hasSameTag(tag: Tag): Boolean = tag == this.tag

    /**
     * このタグの可視・不可視を制御する.
     * 
     * 引数で渡したテキストが、このビューの持つタグにマッチする場合、
     * このビューは可視状態になり、マッチしない場合は不可視になる.
     * 
     * @param text 表示条件となるテキスト
     */
    fun controlVisibility(text: String) {
        isVisible = tag.matches(text)
    }
}
