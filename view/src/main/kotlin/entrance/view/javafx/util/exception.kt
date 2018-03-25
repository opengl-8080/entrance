package entrance.view.javafx.util


fun extractRootCause(e: Throwable): Throwable {
    fun causeException(e: Throwable): Throwable {
        val cause = e.cause
        
        return if (cause == null) {
            e
        } else {
            causeException(cause)
        }
    }
    
    return causeException(e)
}