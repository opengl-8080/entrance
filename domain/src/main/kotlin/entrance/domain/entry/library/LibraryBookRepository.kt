package entrance.domain.entry.library


interface LibraryBookRepository {
    
    fun save(libraryBook: LibraryBook)
}