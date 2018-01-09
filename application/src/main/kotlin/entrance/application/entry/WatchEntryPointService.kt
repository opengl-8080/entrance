package entrance.application.entry

import entrance.domain.entry.AddedEntryImageEventSubscriber
import entrance.domain.entry.EntryPoint
import org.springframework.stereotype.Component

@Component
class WatchEntryPointService(
    private val entryPoint: EntryPoint
) {
    
    fun watch(subscriber: AddedEntryImageEventSubscriber) {
        entryPoint.watch(subscriber)
    }
}
