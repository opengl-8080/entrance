package entrance.application.deletion

import entrance.domain.image.Image
import entrance.domain.image.ImageRepository
import entrance.domain.similar.SimilarImageIndexDeleter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DeleteImageService (
    private val imageRepository: ImageRepository,
    private val similarImageIndexDeleter: SimilarImageIndexDeleter
) {
    private val logger = LoggerFactory.getLogger(DeleteImageService::class.java)
    
    fun delete(image: Image) {
        logger.info("delete image = ${image.stringRelativePath}")
        logger.info("delete from database")
        imageRepository.delete(image)
        logger.info("delete index")
        similarImageIndexDeleter.deleteIndex(image)
        logger.info("delete local file")
        image.deleteLocalFile()
    }
}