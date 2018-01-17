package entrance.domain

/**
 * ドメインオブジェクトが許容しない値が渡されたことを表す例外.
 * 
 * @constructor
 * @param message エラーメッセージ
 */
class InvalidValueException(message: String): Exception(message)
