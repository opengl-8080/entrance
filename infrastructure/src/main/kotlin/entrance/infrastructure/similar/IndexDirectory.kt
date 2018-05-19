package entrance.infrastructure.similar

import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.DeprecatedRelativePath
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class IndexDirectory (
    private val home: EntranceHome
) {
    
    val path: Path
        get() = home.initDir(DeprecatedRelativePath("indexes")).path
    
}
