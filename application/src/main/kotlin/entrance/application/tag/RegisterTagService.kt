package entrance.application.tag

import entrance.domain.tag.Tag
import entrance.domain.tag.TagFilterWord
import entrance.domain.tag.TagName
import entrance.domain.tag.TagRepository
import org.springframework.stereotype.Component

@Component
class RegisterTagService (
    private val tagRepository: TagRepository
) {
    
    fun register(tagName: TagName, tagFilterWord: TagFilterWord) {
        val tag = Tag.new(tagName, tagFilterWord)
        tagRepository.register(tag)
    }
}