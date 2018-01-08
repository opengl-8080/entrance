package entrance.domain

class RelationalTags (
    var mainTag: Tag? = null,
    val tags: MutableList<Tag> = mutableListOf()
) {
}