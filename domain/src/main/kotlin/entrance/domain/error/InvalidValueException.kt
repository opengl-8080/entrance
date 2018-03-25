package entrance.domain.error

/**
 * ドメインオブジェクトが許容しない値が渡されたことを表す例外.
 * 
 * @constructor
 * @param errorMessage エラーメッセージ
 */
class InvalidValueException(
    private val errorMessage: ErrorMessage,
    override var message: String = errorMessage.message
): Exception(message)
