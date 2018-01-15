package entrance.view.javafx

import javafx.scene.Parent

data class FxmlContext<out T> (
    val root: Parent,
    val controller: T
)
