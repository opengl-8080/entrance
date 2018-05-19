package entrance.domain.entry.entrance

import entrance.domain.base.file.LocalDirectory
import entrance.domain.base.file.RelativePath
import entrance.domain.base.item.image.ImageFile
import entrance.domain.util.config.EntranceHome
import org.springframework.stereotype.Component

@Component
class EntryDirectory (
    entranceHome: EntranceHome
) {
    private val dir: LocalDirectory = entranceHome.resolveDirectory(RelativePath("entry")).createIfNotExists()
    
    fun getEntryImages(): EntryImages {
        val entryImageList = dir.getFiles { ImageFile.isImageFile(it) }.map { EntryImage(it) }
        return EntryImages(entryImageList)
    }
    
    fun getEntryBooks(): EntryBooks {
        val entryBookList = dir.getDirectories().map { EntryBook(it) }
        return EntryBooks(entryBookList)
    }
}
