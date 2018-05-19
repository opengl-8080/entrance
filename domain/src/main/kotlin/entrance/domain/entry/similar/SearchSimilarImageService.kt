package entrance.domain.entry.similar

import entrance.domain.entry.entrance.EntryImage

interface SearchSimilarImageService {
    
    fun search(entryImage: EntryImage): SimilarImages
}