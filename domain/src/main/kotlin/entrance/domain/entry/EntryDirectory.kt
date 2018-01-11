package entrance.domain.entry

import java.nio.file.Path

interface EntryDirectory {
    
    fun path(): Path
    fun getAllEntryImages(): AllEntryImages
}
