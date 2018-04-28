package entrance.application.tag

import entrance.domain.tag.category.NewTagCategory
import entrance.domain.tag.category.TagCategoryName
import entrance.domain.tag.category.TagCategoryRepository
import entrance.domain.util.error.ErrorMessage
import entrance.domain.util.error.InvalidValueException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterTagCategoryService(
    private val tagCategoryRepository: TagCategoryRepository
) {
    
    fun register(tagCategoryName: TagCategoryName) {
        val alreadyExistsTag = tagCategoryRepository.find(tagCategoryName)
        if (alreadyExistsTag == null) {
            val tag = NewTagCategory(tagCategoryName)
            tagCategoryRepository.register(tag)
        } else {
            throw InvalidValueException(ErrorMessage("${tagCategoryName.value} は既に存在します"))
        }
    }
}