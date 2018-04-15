package entrance.infrastructure.entry

import entrance.domain.entry.EntryDirectory
import entrance.domain.util.file.LocalFile
import entrance.domain.util.file.RelativePath
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds

/**
 * ディレクトリの監視機能を簡潔に提供するクラス.
 */
class DirectoryWatcher (
    private val dir: Path,
    private val entryDirectory: EntryDirectory
) {
    
    fun watchFileCreatedEvent(listener: (LocalFile) -> Unit) {
        val watcher = FileSystems.getDefault().newWatchService()
        dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY)
        
        do {
            val key = watcher.take()
            
            key.pollEvents().forEach { event ->
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    val relativePath = RelativePath(event.context() as Path)
                    val localFile = entryDirectory.resolveFile(relativePath)
                    listener(localFile)
                }
            }
        } while (key.reset())
        
        throw IllegalStateException("ディレクトリの監視が中断されました. dir=$dir")
    }
}