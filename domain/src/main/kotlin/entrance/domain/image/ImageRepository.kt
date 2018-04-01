package entrance.domain.image

import entrance.domain.tag.Tag

interface ImageRepository {
    
    fun find(tagList: List<Tag>): List<Image>
}