package entrance.domain

class RelationalTags (
    private var mainTag: Tag? = null,
    private val tags: MutableList<Tag> = mutableListOf()
) {
}