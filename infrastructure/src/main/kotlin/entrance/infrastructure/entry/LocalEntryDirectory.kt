package entrance.infrastructure.entry

import entrance.domain.config.EntranceHome
import entrance.domain.entry.AllEntryImages
import entrance.domain.entry.EntryDirectory
import entrance.domain.entry.EntryImage
import entrance.domain.file.AbsolutePath
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

@Component
class LocalEntryDirectory (
    entranceHome: EntranceHome
): EntryDirectory {
    
    private val dir = entranceHome.path().resolve("entry").also { dir ->
        if (Files.notExists(dir)) {
            Files.createDirectories(dir)
        }
    }
    
    override fun getAllEntryImages() = AllEntryImages(Files
            .list(dir)
            .filter(::isImageFile)
            .map { file -> EntryImage(AbsolutePath(file.toAbsolutePath())) }
            .toList())
    
    private fun isImageFile(file: Path): Boolean {
        val name = file.fileName.toString()
        return listOf("jpg", "jpeg", "png", "gif", "bmp")
                .any { extension -> name.endsWith(extension, ignoreCase = true) }
    }
}