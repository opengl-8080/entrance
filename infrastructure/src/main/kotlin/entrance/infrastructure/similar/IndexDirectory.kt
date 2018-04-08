package entrance.infrastructure.similar

import entrance.domain.config.EntranceHome
import entrance.domain.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class IndexDirectory (
    private val home: EntranceHome
) {
    
    val path: Path
        get() = home.initDir(RelativePath("indexes")).path
    
}
