package entrance.infrastructure.entry

import entrance.domain.entry.EntryDirectory
import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class LocalEntryDirectory (
    entranceHome: EntranceHome
): EntryDirectory {
    private val dir = entranceHome.initDir(RelativePath("entry"))

    override fun resolveFile(relativePath: RelativePath): LocalFile = dir.resolveFile(relativePath)
    override fun path(): Path = dir.path
    
}