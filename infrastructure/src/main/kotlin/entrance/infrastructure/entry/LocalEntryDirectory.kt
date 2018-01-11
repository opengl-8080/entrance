package entrance.infrastructure.entry

import entrance.domain.config.EntranceHome
import entrance.domain.entry.AllEntryImages
import entrance.domain.entry.EntryDirectory
import entrance.domain.entry.EntryImage
import entrance.domain.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class LocalEntryDirectory (
    entranceHome: EntranceHome
): EntryDirectory {

    private val dir = entranceHome.initDir(RelativePath("entry"))
    
    override fun getAllEntryImages() = AllEntryImages(dir.files().filter { it.isImage() }.map(::EntryImage))
    override fun path(): Path = dir.path
    
}