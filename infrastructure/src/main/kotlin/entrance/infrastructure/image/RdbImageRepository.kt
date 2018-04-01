package entrance.infrastructure.image

import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.domain.image.Image
import entrance.domain.image.ImageRepository
import entrance.domain.tag.Tag
import entrance.infrastructure.database.ImageTableDao
import org.springframework.stereotype.Component

@Component
class RdbImageRepository(
    private val imageTableDao: ImageTableDao,
    private val libraryDirectory: LibraryDirectory
): ImageRepository {
    
    override fun find(tagList: List<Tag>): List<Image> {
        val categorizedTagList = imageTableDao.findCategorizedImagesByTagIdList(tagList.map { it.id.value })
        return categorizedTagList.map { it ->
            val localFile = libraryDirectory.resolveFile(RelativePath(it.path))
            Image(localFile)
        }
    }
}