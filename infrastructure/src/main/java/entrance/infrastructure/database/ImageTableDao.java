package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface ImageTableDao {
    
    @Select
    ImageTable find(long itemId);
    
    @Insert
    int insert(ImageTable imageTable);
}
