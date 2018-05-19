package entrance.domain.entry.similar

import entrance.domain.entry.library.LibraryImage


interface SimilarImageIndexer {
    
    fun indexSimilarImage(libraryImage: LibraryImage)
}