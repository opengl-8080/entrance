package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface ItemTableDao {

    @Select
    ItemTable find(Long id);
    
    @Insert
    int insert(ItemTable itemTable);
    
    @Update
    int update(ItemTable itemTable);
}
