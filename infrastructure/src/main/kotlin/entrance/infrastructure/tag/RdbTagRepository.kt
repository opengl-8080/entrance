package entrance.infrastructure.tag

import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import entrance.infrastructure.database.TagFilterWordTableDao
import entrance.infrastructure.database.TagTableDao
import org.springframework.stereotype.Component

@Component
class RdbTagRepository(
    private val tagTableDao: TagTableDao,
    private val tagFilterWordTableDao: TagFilterWordTableDao
): TagRepository {
    
    override fun register(tag: Tag) {
        
        
    }
}