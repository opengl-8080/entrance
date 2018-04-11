package entrance.infrastructure.similar

import entrance.domain.image.Image
import entrance.domain.similar.SimilarImageIndexDeleter
import net.semanticmetadata.lire.builders.DocumentBuilder
import net.semanticmetadata.lire.utils.LuceneUtils
import org.apache.lucene.index.Term
import org.apache.lucene.store.FSDirectory
import org.springframework.stereotype.Component

@Component
class LireIndexDeleter(
    private val indexDirectory: IndexDirectory
): SimilarImageIndexDeleter {
    
    override fun deleteIndex(image: Image) {

        LuceneUtils.createIndexWriter(FSDirectory.open(indexDirectory.path), false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer).use { writer ->
            val term = Term(DocumentBuilder.FIELD_NAME_IDENTIFIER, image.stringRelativePath)
            writer.deleteDocuments(term)
        }
    }
}