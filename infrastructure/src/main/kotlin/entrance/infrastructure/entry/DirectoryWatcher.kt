package entrance.infrastructure.entry

import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds

/**
 * ディレクトリの監視機能を簡潔に提供するクラス.
 * 
 * 
 */
class DirectoryWatcher (
    private val dir: Path
) {
    
    fun watchFileCreatedEvent(listener: () -> Unit) {
        val watcher = FileSystems.getDefault().newWatchService()
        dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)
        println("watching dir=$dir")
        
        do {
            val key = try {
                watcher.take()
            } catch (e: InterruptedException) {
                return
            }

            val created = key.pollEvents().any {it.kind() == StandardWatchEventKinds.ENTRY_CREATE}
            println("created=$created")
            if (created) {
                listener()
            }
        } while (key.reset())
        
        throw IllegalStateException("ディレクトリの監視が中断されました. dir=$dir")
    }
}