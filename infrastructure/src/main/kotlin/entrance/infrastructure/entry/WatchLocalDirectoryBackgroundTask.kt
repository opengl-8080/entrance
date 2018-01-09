package entrance.infrastructure.entry

import entrance.application.entry.WatchEntryPointService
import entrance.domain.entry.AddedEntryImageEventSubscriber
import entrance.domain.entry.EntryImage
import javafx.concurrent.Task
import org.springframework.stereotype.Component

@Component
class WatchLocalDirectoryBackgroundTask(
    private val watchEntryPointService: WatchEntryPointService
) : AddedEntryImageEventSubscriber {
    
    fun start() {
        val thread = Thread(object: Task<Void?>() {
            override fun call(): Void? {
                watchEntryPointService.watch(this@WatchLocalDirectoryBackgroundTask)
                return null
            }
        })
        thread.isDaemon = true
        thread.start()
    }

    override fun subscribe(addedEntryImage: EntryImage) {
        println("addedEntryImage=$addedEntryImage")
    }
}