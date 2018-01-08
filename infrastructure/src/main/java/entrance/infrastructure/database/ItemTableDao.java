package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ItemTableDao {
    @Select
    List<ItemTable> selectAll();

    @Select
    ItemTable find(Long itemId);
    
    @Insert
    void save(ItemTable itemTable);
}
