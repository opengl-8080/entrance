package entrance.view.javafx.window.viewer

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import java.util.function.BiConsumer
import java.util.function.Consumer


class MouseGesture {
    private var leftClicked: Boolean = false
    private var rightClicked: Boolean = false
    private var previousScreenX: Double = 0.toDouble()
    private var previousScreenY: Double = 0.toDouble()

    fun bind(root: Node) {
        root.onMouseReleased = EventHandler { this.onMouseReleased(it) }
        root.onMouseDragged = EventHandler { this.onMouseDragged(it) }
        root.onScroll = EventHandler { this.onScrolled(it) }
        root.onMousePressed = EventHandler { this.onMousePressed(it) }
    }

    private var leftDraggedListener: BiConsumer<Double, Double>? = null
    fun onLeftDragged(leftDraggedListener: BiConsumer<Double, Double>) {
        this.leftDraggedListener = leftDraggedListener
    }

    private var rightScrolledListener: Consumer<Double>? = null
    fun onRightScrolled(rightScrolledListener: Consumer<Double>) {
        this.rightScrolledListener = rightScrolledListener
    }

    private var scrolledListener: Consumer<Double>? = null
    fun onScrolled(scrolledListener: Consumer<Double>) {
        this.scrolledListener = scrolledListener
    }

    private var mousePressedListener: Consumer<MouseEvent>? = null
    fun onMousePressed(mousePressedListener: Consumer<MouseEvent>) {
        this.mousePressedListener = mousePressedListener
    }

    private var mouseReleasedListener: Consumer<MouseEvent>? = null
    fun onMouseReleased(mouseEventConsumer: Consumer<MouseEvent>) {
        this.mouseReleasedListener = mouseEventConsumer
    }

    private fun onMousePressed(e: MouseEvent) {
        leftClicked = e.isPrimaryButtonDown
        rightClicked = e.isSecondaryButtonDown
        previousScreenX = e.screenX
        previousScreenY = e.screenY
        mousePressedListener!!.accept(e)
    }

    private fun onMouseReleased(e: MouseEvent) {
        leftClicked = false
        rightClicked = false
        mouseReleasedListener!!.accept(e)
    }

    private fun onMouseDragged(e: MouseEvent) {
        if (leftClicked) {
            val dx = e.screenX - previousScreenX
            val dy = e.screenY - previousScreenY
            leftDraggedListener!!.accept(dx, dy)
            previousScreenX = e.screenX
            previousScreenY = e.screenY
        }
    }

    private fun onScrolled(e: ScrollEvent) {
        if (rightClicked) {
            rightScrolledListener!!.accept(e.deltaY)
        }

        if (!leftClicked && !rightClicked) {
            scrolledListener!!.accept(e.deltaY)
        }
    }
}