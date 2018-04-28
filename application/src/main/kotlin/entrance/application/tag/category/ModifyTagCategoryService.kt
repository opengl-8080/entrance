package entrance.application.tag

import entrance.domain.tag.category.TagCategory
import entrance.domain.tag.category.TagCategoryName
import entrance.domain.tag.category.TagCategoryRepository
import entrance.domain.util.error.ErrorMessage
import entrance.domain.util.error.InvalidValueException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModifyTagCategoryService(
    private val tagCategoryRepository: TagCategoryRepository
) {
    
    fun modify(tagCategory: TagCategory, tagCategoryName: TagCategoryName) {
        val modifiableTagCategory = tagCategoryRepository.findForUpdate(tagCategory.name)
                ?: throw InvalidValueException(ErrorMessage("タグカテゴリが存在しません。同時更新されている可能性があります。"))

        val sameNameTagCategory = tagCategoryRepository.find(tagCategoryName)
        if (sameNameTagCategory != null && sameNameTagCategory.id != tagCategory.id) {
            throw InvalidValueException(ErrorMessage(""""${tagCategoryName.value}" というタグカテゴリ名は既に使用されています"""))
        }
        
        modifiableTagCategory.name = tagCategoryName
        
        tagCategoryRepository.modify(modifiableTagCategory)
    }
}
