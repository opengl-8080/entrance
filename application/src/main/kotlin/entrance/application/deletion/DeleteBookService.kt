package entrance.application.deletion

import entrance.domain.viewer.book.StoredBook
import entrance.domain.viewer.book.StoredBookRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteBookService (
    private val storedBookRepository: StoredBookRepository
) {
    private val logger = LoggerFactory.getLogger(DeleteBookService::class.java)
    
    fun delete(storedBook: StoredBook) {
        logger.info("delete book = " + storedBook.relativePath.asString())
        logger.debug("delete from database")
        storedBookRepository.delete(storedBook)
        logger.debug("delete from local")
        storedBook.delete()
    }
}