package entrance.infrastructure.entry

import entrance.application.entry.SaveEnteredItemService
import entrance.domain.entry.EntryDirectory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class WatchingEntryDirectoryTask (
    private val entryDirectory: EntryDirectory,
    private val saveEnteredItemService: SaveEnteredItemService
) {
    private val logger = LoggerFactory.getLogger(WatchingEntryDirectoryTask::class.java)
    
    fun begin() {
        thread(isDaemon = true, name = "WatchingEntryDirectoryThread") {
            logger.info("start watching entry directory (${entryDirectory.path()})")
            val watcher = DirectoryWatcher(entryDirectory.path())
            watcher.watchFileCreatedEvent { 
                entryDirectory.getAllEntryImages().forEachImages { entryImage ->
                    saveEnteredItemService.save(entryImage)
                }
            }
        }
    }
}