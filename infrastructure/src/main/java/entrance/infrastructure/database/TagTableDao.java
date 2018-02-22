package entrance.infrastructure.database;

import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface TagTableDao {
    
    @Select
    @NotNull
    List<TagTable> findAll();
    
    @Select
    TagTable find(Long id);
    
    @Insert
    int insert(TagTable tagTable);
}
