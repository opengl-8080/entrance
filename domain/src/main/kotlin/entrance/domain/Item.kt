package entrance.domain

abstract class Item (
    private var id: ItemId? = null,
    var uncategorized: Boolean = true,
    val registeredDateTime: RegisteredDateTime = RegisteredDateTime.now(),
    val relationalTags: RelationalTags = RelationalTags()
) {
}
