package entrance.infrastructure.similar

import entrance.domain.entry.library.LibraryDirectory
import entrance.domain.entry.library.LibraryImage
import entrance.domain.entry.similar.SimilarImageIndexer
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
    
    override fun indexSimilarImage(libraryImage: LibraryImage) {
        val globalDocumentBuilder = GlobalDocumentBuilder(CEDD::class.java)
        
        LuceneUtils.createIndexWriter(FSDirectory.open(indexDirectory.path), false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer).use { writer ->
            val localFile = libraryDirectory.resolveFile(libraryImage.relativePath)
            val bufferedImage = ImageIO.read(localFile.javaFile)
            val document = globalDocumentBuilder.createDocument(bufferedImage, libraryImage.relativePath.value)
            writer.addDocument(document)
            logger.info("indexing image = ${libraryImage.relativePath}")
        }
    }
}