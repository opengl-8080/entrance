package entrance.domain.similar

data class SimilarImageHandleResult (
    val saveEntryImage: Boolean
) {
    companion object {
        fun save() = SimilarImageHandleResult(true)
        fun noSave() = SimilarImageHandleResult(false)
    }
}