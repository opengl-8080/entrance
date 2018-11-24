package entrance.infrastructure.doma

import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Level

@Configuration
class EntranceDomaConfig {
    
    @Bean
    fun domaConfigBuilder(): DomaConfigBuilder {
        val builder = DomaConfigBuilder()
        builder.jdbcLogger(UtilLoggingJdbcLogger(Level.FINE))
        return builder
    }
}