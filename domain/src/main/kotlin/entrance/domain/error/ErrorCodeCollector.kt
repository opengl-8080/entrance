package entrance.domain.error


class ErrorCodeCollector {
    
    fun collect(vararg blocks: () -> Unit): List<ErrorCode> {
        val errorCodeList = mutableListOf<ErrorCode>()
        blocks.forEach { block ->
            try {
                block()
            } catch (e: InvalidValueException) {
                errorCodeList += e.errorCode
            }
        }
        return errorCodeList.toList()
    }
}