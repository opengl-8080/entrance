package entrance.infrastructure.similar

import entrance.domain.entry.EntryImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.file.RelativePath
import entrance.domain.similar.SimilarImage
import entrance.domain.similar.SimilarImageFinder
import net.semanticmetadata.lire.builders.DocumentBuilder
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.store.FSDirectory
import org.springframework.stereotype.Component
import java.nio.file.Files
import javax.imageio.ImageIO

@Component
class LireSimilarImageFinder (
    private val indexDirectory: IndexDirectory,
    private val libraryDirectory: LibraryDirectory
): SimilarImageFinder {
    
    override fun findSimilarImage(entryImage: EntryImage): List<SimilarImage> {
        val similarImages = mutableListOf<SimilarImage>()
        
        if (indexDirectory.path.toFile().listFiles().isEmpty()) {
            return similarImages
        }
        
        DirectoryReader.open(FSDirectory.open(indexDirectory.path)).use { reader ->
            val searcher = GenericFastImageSearcher(5, CEDD::class.java)
            val image = ImageIO.read(entryImage.path.toFile())
            val hits = searcher.search(image, reader)


            for (i in 0 until hits.length()) {
                val score = hits.score(i)

                if (score < 5.0) {
                    val document = reader.document(hits.documentID(i))
                    val values = document.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)
                    val filePath = values[0]
                    val localFile = libraryDirectory.resolveFile(RelativePath(filePath))
                    similarImages.add(SimilarImage(localFile))
                }
            }
        }
        
        return similarImages
    }
}