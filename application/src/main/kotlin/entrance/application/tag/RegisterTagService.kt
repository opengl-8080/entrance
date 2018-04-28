package entrance.application.tag

import entrance.domain.tag.NewTag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import entrance.domain.tag.category.TagCategory
import entrance.domain.util.error.ErrorMessage
import entrance.domain.util.error.InvalidValueException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterTagService (
    private val tagRepository: TagRepository
) {
    
    fun register(tagName: TagName, tagFilterWord: TagFilterWord, tagCategory: TagCategory) {
        val alreadyExistsTag = tagRepository.find(tagName)
        if (alreadyExistsTag == null) {
            val tag = NewTag(tagName, tagFilterWord, tagCategory)
            tagRepository.register(tag)
        } else {
            throw InvalidValueException(ErrorMessage("${tagName.value} は既に存在します"))
        }
    }
}