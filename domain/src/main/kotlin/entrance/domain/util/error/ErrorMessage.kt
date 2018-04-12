package entrance.domain.util.error


class ErrorMessage(val message: String) {
    fun throwAsInvalidValueException() {
        throw InvalidValueException(this)
    }
}
