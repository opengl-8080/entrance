package entrance.application.tag

import entrance.domain.error.ErrorMessage
import entrance.domain.error.InvalidValueException
import entrance.domain.tag.NewTag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterTagService (
    private val tagRepository: TagRepository
) {
    
    fun register(tagName: TagName, tagFilterWord: TagFilterWord) {
        val alreadyExistsTag = tagRepository.find(tagName)
        if (alreadyExistsTag == null) {
            val tag = NewTag(tagName, tagFilterWord)
            tagRepository.register(tag)
        } else {
            throw InvalidValueException(ErrorMessage("${tagName.value} は既に存在します"))
        }
    }
}