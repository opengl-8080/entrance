package entrance.application.deletion

import entrance.domain.viewer.image.StoredImage
import entrance.domain.viewer.image.StoredImageRepository
import entrance.domain.similar.SimilarImageIndexDeleter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DeleteImageService (
        private val storedImageRepository: StoredImageRepository,
        private val similarImageIndexDeleter: SimilarImageIndexDeleter
) {
    private val logger = LoggerFactory.getLogger(DeleteImageService::class.java)
    
    fun delete(storedImage: StoredImage) {
        logger.info("delete image = ${storedImage.relativePath.value}")
        logger.info("delete from database")
        storedImageRepository.delete(storedImage)
        logger.info("delete index")
        similarImageIndexDeleter.deleteIndex(storedImage)
        logger.info("delete local file")
        storedImage.deleteLocalFile()
    }
}