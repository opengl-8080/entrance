package entrance.infrastructure.entry

import entrance.domain.entry.AddedEntryImageEventSubscriber
import entrance.domain.entry.EntryImage
import entrance.domain.entry.EntryImageName
import entrance.domain.entry.EntryPoint
import org.springframework.stereotype.Component
import java.nio.file.Paths

@Component
class LocalDirectoryEntryPoint: EntryPoint {
    
    private var directory = Paths.get(System.getProperty("user.home"))
    
    override fun watch(subscriber: AddedEntryImageEventSubscriber) {
        subscriber.subscribe(EntryImage(EntryImageName("hoge")))
    }
}
