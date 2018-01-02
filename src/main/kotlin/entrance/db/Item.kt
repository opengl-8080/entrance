package entrance.db

import org.seasar.doma.*
import org.seasar.doma.jdbc.entity.NamingType
import java.time.LocalDateTime

@Entity(immutable = true, naming = NamingType.SNAKE_UPPER_CASE)
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(name="IS_UNCATEGORIZED")
    val uncategorized: Boolean,
    
    val registeredDateTime: LocalDateTime 
)
