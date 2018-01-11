package entrance.infrastructure.entry

import entrance.application.entry.SaveEnteredItemService
import entrance.domain.entry.EntryDirectory
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class WatchingEntryDirectoryTask (
    private val entryDirectory: EntryDirectory,
    private val saveEnteredItemService: SaveEnteredItemService
) {
    
    fun begin() {
        thread(isDaemon = true, name = "WatchingEntryDirectoryThread") {
            val watcher = DirectoryWatcher(entryDirectory.path())
            watcher.watchFileCreatedEvent { 
                println("created!!")
                entryDirectory.getAllEntryImages().forEachImages { entryImage -> 
                    saveEnteredItemService.save(entryImage)
                }
            }
        }
    }
}