package entrance.infrastructure.entry

import entrance.application.entry.SaveEnteredItemService
import entrance.application.similar.FilterSimilarImageService
import entrance.domain.entry.EntryDirectory
import entrance.domain.entry.EntryImage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class WatchingEntryDirectoryTask (
    private val entryDirectory: EntryDirectory,
    private val saveEnteredItemService: SaveEnteredItemService,
    private val filterSimilarImageService: FilterSimilarImageService
) {
    private val logger = LoggerFactory.getLogger(WatchingEntryDirectoryTask::class.java)
    
    fun begin() {
        thread(isDaemon = true, name = "WatchingEntryDirectoryThread") {
            logger.info("start watching entry directory (${entryDirectory.path()})")
            val watcher = DirectoryWatcher(entryDirectory.path(), entryDirectory)
            watcher.watchFileCreatedEvent { localFile ->
                if (localFile.isImage()) {
                    val entryImage = EntryImage(localFile)
                    
                    if (filterSimilarImageService.decideToSave(entryImage)) {
                        saveEnteredItemService.save(entryImage)
                    }
                }
            }
        }
    }
}