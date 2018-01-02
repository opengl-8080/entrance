package entrance.db

import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable
import org.springframework.transaction.annotation.Transactional

@Dao
@ConfigAutowireable
interface ItemDao {
    @Select
    fun selectAll(): List<Item>
    
    @Insert
    @Transactional
    fun insert(item: Item)
}