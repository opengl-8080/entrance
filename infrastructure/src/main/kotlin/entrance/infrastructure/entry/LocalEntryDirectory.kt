package entrance.infrastructure.entry

import entrance.domain.entry.AllEntryImages
import entrance.domain.entry.EntryDirectory
import entrance.domain.entry.EntryImage
import entrance.domain.util.config.EntranceHome
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

@Component
class LocalEntryDirectory (
    entranceHome: EntranceHome
): EntryDirectory {

    private val dir = entranceHome.initDir(RelativePath("entry"))

    override fun readAllImages(): AllEntryImages {
        val images = Files.list(dir.path)
                                        .map { LocalFile(it) }
                                        .filter { it.isImage() }
                                        .map { EntryImage(it) }
                                        .toList()
        
        return AllEntryImages(images)
    }

    override fun resolveFile(relativePath: RelativePath): LocalFile = dir.resolveFile(relativePath)
    override fun path(): Path = dir.path
    
}