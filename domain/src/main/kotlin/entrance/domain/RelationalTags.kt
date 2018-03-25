package entrance.domain

import entrance.domain.tag.NewTag

class RelationalTags (
        var mainTag: NewTag? = null,
        val tags: MutableList<NewTag> = mutableListOf()
) {
}