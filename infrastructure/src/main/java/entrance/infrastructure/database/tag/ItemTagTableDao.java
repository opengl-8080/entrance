package entrance.infrastructure.database.tag;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ItemTagTableDao {

    @Select
    List<ItemTagTable> findByItemId(Long itemId);
    
    @Insert
    int insert(ItemTagTable itemTagTable);
    
    @Delete
    int delete(ItemTagTable itemTagTable);
}
