package entrance.infrastructure.management

import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.domain.management.ManagedImage
import entrance.domain.management.ManagedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagId
import entrance.domain.tag.TagName
import entrance.infrastructure.database.ImageTableDao
import entrance.infrastructure.database.TagTableDao
import org.springframework.stereotype.Component

@Component
class RdbManagedImageRepository(
    private val imageTableDao: ImageTableDao,
    private val tagTableDao: TagTableDao,
    private val libraryDirectory: LibraryDirectory
): ManagedImageRepository {
    
    override fun find(tagList: List<Tag>): List<ManagedImage> {
        val categorizedTagList = imageTableDao.findCategorizedImagesByTagIdList(tagList.map { it.id.value })
        return categorizedTagList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            val tagList = tagTableDao.findByItemId(it.id)
                    .map {
                        val tagId = TagId(it.id)
                        val tagName = TagName(it.name)
                        val tagFilterWord = TagFilterWord(it.filterWord)
                        
                        Tag(tagId, tagName, tagFilterWord)
                    }
            ManagedImage(localFile, tagList)
        }
    }
}