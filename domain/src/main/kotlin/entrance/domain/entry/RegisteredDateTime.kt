package entrance.domain.entry

import java.time.LocalDateTime

data class RegisteredDateTime(
    val value: LocalDateTime
) {
    companion object {
        fun now() = RegisteredDateTime(LocalDateTime.now())
    }
}
