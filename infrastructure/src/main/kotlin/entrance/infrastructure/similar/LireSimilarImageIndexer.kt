package entrance.infrastructure.similar

import entrance.domain.entry.EnteredImage
import entrance.domain.entry.LibraryDirectory
import entrance.domain.similar.SimilarImageIndexer
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD
import net.semanticmetadata.lire.utils.LuceneUtils
import org.apache.lucene.store.FSDirectory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.imageio.ImageIO

@Component
class LireSimilarImageIndexer (
    private val indexDirectory: IndexDirectory,
    private val libraryDirectory: LibraryDirectory
): SimilarImageIndexer {
    private val logger = LoggerFactory.getLogger(LireSimilarImageIndexer::class.java)
    
    override fun indexSimilarImage(enteredImage: EnteredImage) {
        val globalDocumentBuilder = GlobalDocumentBuilder(CEDD::class.java)
        
        LuceneUtils.createIndexWriter(FSDirectory.open(indexDirectory.path), false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer).use { writer ->
            val localFile = libraryDirectory.resolveFile(enteredImage.path)
            val bufferedImage = ImageIO.read(localFile.path.toFile())
            val document = globalDocumentBuilder.createDocument(bufferedImage, enteredImage.stringPath)
            writer.addDocument(document)
            logger.info("indexing image = ${enteredImage.stringPath}")
        }
    }
}