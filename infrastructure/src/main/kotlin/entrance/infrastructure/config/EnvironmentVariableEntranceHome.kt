package entrance.infrastructure.config

import entrance.domain.config.EntranceHome
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class EnvironmentVariableEntranceHome: EntranceHome {

    @Value("\${ENTRANCE_HOME:.}")
    private lateinit var home: Path

    override fun path() = home
}