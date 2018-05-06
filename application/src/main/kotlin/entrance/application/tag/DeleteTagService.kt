package entrance.application.tag

import entrance.domain.RankCondition
import entrance.domain.tag.SelectedTagSet
import entrance.domain.categorization.image.CategorizedImageRepository
import entrance.domain.tag.Tag
import entrance.domain.tag.TagRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteTagService (
    private val tagRepository: TagRepository,
    private val imageRepository: CategorizedImageRepository
) {
    private val logger = LoggerFactory.getLogger(DeleteTagService::class.java)
    
    fun delete(tag: Tag) {
        // lock
        val mutableTag = tagRepository.findForUpdate(tag.name)

        val selectedTagSet = SelectedTagSet(tag)
        
        val images = imageRepository.findTaggedImages(selectedTagSet, RankCondition.ALL)
        images.forEach { image ->
            image.remove(selectedTagSet)
            imageRepository.save(image)
            logger.debug("remove tag (${tag.name.value}) from image (${image.uri})")
        }
        
        logger.info("delete tag (${tag.name.value})")
        tagRepository.delete(tag)
    }
}