package entrance.domain

import entrance.domain.tag.Tag

class RelationalTags (
        var mainTag: Tag? = null,
        val tags: MutableList<Tag> = mutableListOf()
) {
}