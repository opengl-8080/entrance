package entrance.infrastructure.similar

import entrance.domain.base.file.RelativePath
import entrance.domain.entry.entrance.EntryImage
import entrance.domain.entry.library.LibraryDirectory
import entrance.domain.entry.similar.SearchSimilarImageService
import entrance.domain.entry.similar.SimilarImage
import entrance.domain.entry.similar.SimilarImages
import entrance.infrastructure.util.Retry
import net.semanticmetadata.lire.builders.DocumentBuilder
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.store.FSDirectory
import org.springframework.stereotype.Component
import javax.imageio.IIOException

@Component
class LireSearchSimilarImageService (
    private val indexDirectory: IndexDirectory,
    private val libraryDirectory: LibraryDirectory
): SearchSimilarImageService {
    
    override fun search(entryImage: EntryImage): SimilarImages {
        val similarImages = mutableListOf<SimilarImage>()
        
        if (indexDirectory.path.toFile().listFiles().isEmpty()) {
            return SimilarImages(similarImages)
        }
        
        DirectoryReader.open(FSDirectory.open(indexDirectory.path)).use { reader ->
            val searcher = GenericFastImageSearcher(5, CEDD::class.java)
            
            val retry = Retry { e -> isImageReadException(e) }
            val image = retry.with { entryImage.readBufferedImage() }
            val hits = searcher.search(image, reader)

            for (i in 0 until hits.length()) {
                val score = hits.score(i)

                if (score < 2.5) {
                    val document = reader.document(hits.documentID(i))
                    val values = document.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)
                    val filePath = values[0]
                    val relativePath = RelativePath(filePath)
                    val localFile = libraryDirectory.resolveFile(relativePath)
                    similarImages.add(SimilarImage(localFile))
                }
            }
        }
        
        return SimilarImages(similarImages)
    }
    
    private fun isImageReadException(e: Exception): Boolean {
        return e is IIOException && e.message == "Can't create an ImageInputStream!"
    }
}