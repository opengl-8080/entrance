package entrance.infrastructure.config

import entrance.domain.util.config.EntranceHomeBase
import entrance.domain.util.file.Directory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class EnvironmentVariableEntranceHome (
    @Value("\${ENTRANCE_HOME:./entrance_home}")
    path: Path
): EntranceHomeBase() {
    
    override val home: Directory = Directory(path)
}