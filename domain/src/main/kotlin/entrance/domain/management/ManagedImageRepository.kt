package entrance.domain.management

import entrance.domain.tag.Tag

interface ManagedImageRepository {

    fun find(tagList: List<Tag>): List<ManagedImage>
}