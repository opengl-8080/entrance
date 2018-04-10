package entrance.infrastructure.util

import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


class Retry (
    private val retryCount: Int = 3,
    private val interval: Long = 500L,
    private val shouldRetry: (Exception) -> Boolean
) {
    private val logger = LoggerFactory.getLogger(Retry::class.java)
    private lateinit var exception: Exception
    
    fun <T> with(process: () -> T): T {
        (0 until retryCount).forEach { i ->
            try {
                if (0 < i) {
                    logger.warn("${i+1} 回目の試行を待機しています...")
                    TimeUnit.MILLISECONDS.sleep(interval)
                }
                
                return process()
            } catch (e: Exception) {
                if (!shouldRetry(e)) {
                    throw e
                }
                logger.warn("${i+1} 回目の試行でエラーが発生しました", e)
                
                exception = e
            }
        }
        
        logger.error("$retryCount 回試行しましたが全てエラーで終わりました")
        
        throw exception
    }
}