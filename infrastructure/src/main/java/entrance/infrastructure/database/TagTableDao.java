package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface TagTableDao {
    @Select
    TagTable find(Long id);
}
