package entrance.domain.entry.book


interface EnteredBookRepository {
    
    fun save(enteredBook: EnteredBook)
}