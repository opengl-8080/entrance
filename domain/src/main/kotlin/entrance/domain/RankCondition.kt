package entrance.domain

data class RankCondition(
    val max: Rank,
    val min: Rank
) {
    companion object {
        val ALL = RankCondition(Rank.FIVE, Rank.ONE)
    }
}
