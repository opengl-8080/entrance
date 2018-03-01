package entrance.view.javafx.error

import entrance.domain.error.ErrorMessage
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField


class Validations(
    block: Validations.() -> Unit
) {
    private val validations = mutableListOf<Validation<*>>()
    
    init {
        this.block()
    }
    
    fun validation(textField: TextField, label: Label, validator: (String) -> ErrorMessage?) {
        validation(label, textField, textField::getText, validator)
    }
    
    fun validation(textArea: TextArea, label: Label, validator: (String) -> ErrorMessage?) {
        validation(label, textArea, textArea::getText, validator)
    }
    
    private fun <T> validation(label: Label, node: Node, valueSupplier: () -> T, validator: (T) -> ErrorMessage?) {
        val validation = Validation(label, node, valueSupplier, validator)
        validations += validation
    }
    
    fun validate(): Boolean {
        var valid = true
        validations.forEach {
            valid = it.validate() && valid
        }
        return valid
    }
}