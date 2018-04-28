package entrance.domain

enum class Rank (val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);
    
    companion object {
        fun of(value: Int): Rank = when (value) {
            1 -> ONE
            2 -> TWO
            3 -> THREE
            4 -> FOUR
            5 -> FIVE
            else -> throw IllegalArgumentException("不明な評価値 value=$value")
        }
    }
}
