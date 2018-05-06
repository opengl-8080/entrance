package entrance.infrastructure.similar

import entrance.domain.similar.SimilarImageIndexDeleter
import entrance.domain.viewer.image.StoredImage
import net.semanticmetadata.lire.builders.DocumentBuilder
import net.semanticmetadata.lire.utils.LuceneUtils
import org.apache.lucene.index.Term
import org.apache.lucene.store.FSDirectory
import org.springframework.stereotype.Component

@Component
class LireIndexDeleter(
    private val indexDirectory: IndexDirectory
): SimilarImageIndexDeleter {
    
    override fun deleteIndex(storedImage: StoredImage) {

        LuceneUtils.createIndexWriter(FSDirectory.open(indexDirectory.path), false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer).use { writer ->
            val term = Term(DocumentBuilder.FIELD_NAME_IDENTIFIER, storedImage.relativePath.asString())
            writer.deleteDocuments(term)
        }
    }
}