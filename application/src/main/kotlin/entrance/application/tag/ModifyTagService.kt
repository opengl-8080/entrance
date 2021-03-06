package entrance.application.tag

import entrance.domain.util.error.ErrorMessage
import entrance.domain.util.error.InvalidValueException
import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import entrance.domain.tag.category.TagCategory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ModifyTagService (
    private val tagRepository: TagRepository
) {
    
    fun modify(tag: Tag, tagName: TagName, filterWord: TagFilterWord, tagCategory: TagCategory) {
        val modifiableTag = tagRepository.findForUpdate(tag.name)
                ?: throw InvalidValueException(ErrorMessage("タグが存在しません。同時更新されている可能性があります。"))

        val sameNameTag = tagRepository.find(tagName)
        if (sameNameTag != null && sameNameTag.id != tag.id) {
            throw InvalidValueException(ErrorMessage(""""${tagName.value}" というタグ名は既に使用されています"""))
        }
        
        modifiableTag.name = tagName
        modifiableTag.filterWord = filterWord
        modifiableTag.tagCategory = tagCategory
        
        tagRepository.modify(modifiableTag)
    }
}