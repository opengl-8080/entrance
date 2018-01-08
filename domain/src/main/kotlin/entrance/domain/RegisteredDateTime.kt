package entrance.domain

import java.time.LocalDateTime

data class RegisteredDateTime(
    val value: LocalDateTime
) {
    companion object {
        fun now() = RegisteredDateTime(LocalDateTime.now())
    }
}
