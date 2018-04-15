package entrance.infrastructure.config

import entrance.domain.util.config.EntranceHomeBase
import entrance.domain.util.file.Directory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Paths

@Component
class EnvironmentVariableEntranceHome (
    @Value("\${ENTRANCE_HOME:./entrance_home}")
    path: String,
    @Value("\${spring.datasource.url}")
    private val jdbcUrl: String
): EntranceHomeBase() {
    
    override val home: Directory = Directory(Paths.get(path))

    override fun jdbcUrl(): String = jdbcUrl
}