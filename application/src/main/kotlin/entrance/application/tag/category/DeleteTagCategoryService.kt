package entrance.application.tag.category

import entrance.domain.tag.TagRepository
import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryName
import entrance.domain.tag.category.TagCategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DeleteTagCategoryService (
    private val tagCategoryRepository: TagCategoryRepository,
    private val tagRepository: TagRepository
) {
    private val logger = LoggerFactory.getLogger(DeleteTagCategoryService::class.java)
    
    fun delete(tagCategory: TagCategory) {
        // lock
        tagCategoryRepository.findForUpdate(tagCategory.name)

        val othersTagCategory = tagCategoryRepository.find(TagCategoryName.OTHERS) ?: throw IllegalStateException("その他のタグがない？")
        
        val usedTagList = tagRepository.findByTagCategoryForUpdate(tagCategory)
        usedTagList.forEach { usedTag ->
            logger.info("update to others tag category. tag=${usedTag.name.value}")
            usedTag.tagCategory = othersTagCategory
            tagRepository.modify(usedTag)
        }
        
        logger.info("delete tag category. category=${tagCategory.name.value}")
        tagCategoryRepository.delete(tagCategory)
    }
}