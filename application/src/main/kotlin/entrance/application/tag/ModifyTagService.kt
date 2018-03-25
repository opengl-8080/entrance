package entrance.application.tag

import entrance.domain.error.ErrorMessage
import entrance.domain.error.InvalidValueException
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModifyTagService (
    private val tagRepository: TagRepository
) {
    
    fun modify(tag: Tag, tagName: TagName, filterWord: TagFilterWord) {
        val modifiableTag = tagRepository.findForUpdate(tag.name)
                ?: throw InvalidValueException(ErrorMessage("タグが存在しません。同時更新されている可能性があります。"))

        val sameNameTag = tagRepository.find(tagName)
        if (sameNameTag != null && sameNameTag.id != tag.id) {
            throw InvalidValueException(ErrorMessage(""""${tagName.value}" というタグ名は既に使用されています"""))
        }
        
        modifiableTag.name = tagName
        modifiableTag.filterWord = filterWord
        
        tagRepository.modify(modifiableTag)
    }
}