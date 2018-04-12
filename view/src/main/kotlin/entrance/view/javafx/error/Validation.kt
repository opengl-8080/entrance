package entrance.view.javafx.error

import entrance.domain.util.error.ErrorMessage
import javafx.scene.Node
import javafx.scene.control.Label

class Validation<T> (
    private val label: Label,
    private val node: Node,
    private val valueSupplier: () -> T,
    private val validator: (T) -> ErrorMessage?
) {
    
    fun validate(): Boolean {
        node.styleClass.remove("error")
        label.text = ""
        
        val value = valueSupplier()
        val errorMessage = validator(value)
        
        if (errorMessage != null) {
            node.styleClass.add("error")
            label.text = errorMessage.message
        }
        
        return errorMessage == null
    }
}