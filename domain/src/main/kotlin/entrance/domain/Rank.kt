package entrance.domain

data class Rank (val value: Short) {
    
    constructor(value: Int): this(value.toShort())
}