package entrance.domain

class ImageItem (
    itemId: ItemId,
    notCategorized: Boolean,
    registeredDateTime: RegisteredDateTime,
    relationalTags: RelationalTags,
    val file: ImageFile
): Item(
    id = itemId,
    notCategorized = notCategorized,
    registeredDateTime = registeredDateTime,
    relationalTags = relationalTags
) {
}
