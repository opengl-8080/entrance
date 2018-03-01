package entrance.domain.error


class ErrorMessage(val message: String) {
    fun throwAsInvalidValueException() {
        throw InvalidValueException(this)
    }
}
