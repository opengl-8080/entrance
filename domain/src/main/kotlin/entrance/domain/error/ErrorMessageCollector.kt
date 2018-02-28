package entrance.domain.error


class ErrorMessageCollector {
    
    private val errorMessageList = mutableListOf<ErrorMessage>()
    
    fun collect(errorMessage: ErrorMessage?): ErrorMessageCollector {
        if (errorMessage != null) {
            errorMessageList += errorMessage
        }
        return this
    }
    
    fun hasError(): Boolean = errorMessageList.isNotEmpty()
    
    fun forEach(iterator: (ErrorMessage) -> Unit) {
        errorMessageList.forEach(iterator)
    }
}