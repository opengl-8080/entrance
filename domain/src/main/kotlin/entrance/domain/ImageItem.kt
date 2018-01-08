package entrance.domain

class ImageItem (
    itemId: ItemId,
    uncategorized: Boolean,
    registeredDateTime: RegisteredDateTime,
    relationalTags: RelationalTags,
    val file: ImageFile
): Item(
    id = itemId,
    uncategorized = uncategorized,
    registeredDateTime = registeredDateTime,
    relationalTags = relationalTags
) {
}
