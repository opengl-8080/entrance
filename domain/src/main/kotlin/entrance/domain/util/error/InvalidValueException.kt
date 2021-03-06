package entrance.domain.util.error

/**
 * ドメインオブジェクトが許容しない値が渡されたことを表す例外.
 * 
 * @constructor
 * @param errorMessage エラーメッセージ
 */
class InvalidValueException(
    private val errorMessage: ErrorMessage,
    override val message: String = errorMessage.message
): Exception(message)
