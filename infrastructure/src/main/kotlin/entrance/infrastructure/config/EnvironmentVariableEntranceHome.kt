package entrance.infrastructure.config

import entrance.domain.base.file.AbsolutePath
import entrance.domain.base.file.LocalDirectory
import entrance.domain.util.config.EntranceHomeBase
import entrance.domain.util.file.DeprecatedDirectory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Path
import java.nio.file.Paths

@Component
class EnvironmentVariableEntranceHome (
    @Value("\${ENTRANCE_HOME:./entrance_home}")
    path: String,
    @Value("\${spring.datasource.url}")
    private val jdbcUrl: String
): EntranceHomeBase() {

    override val path: Path = Paths.get(path)

    override val homeDirectory: LocalDirectory = LocalDirectory(AbsolutePath.fromAmbiguousPath(Paths.get(path)))
    override val home: DeprecatedDirectory = DeprecatedDirectory(Paths.get(path))

    override fun jdbcUrl(): String = jdbcUrl
}