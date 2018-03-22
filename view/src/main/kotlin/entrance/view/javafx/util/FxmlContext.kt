package entrance.view.javafx.util

import javafx.scene.Parent

data class FxmlContext<out T> (
    val root: Parent,
    val controller: T
)
