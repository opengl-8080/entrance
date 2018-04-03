package entrance.infrastructure.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
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
    
    @Select
    List<TagTable> findByItemId(Long itemId);
    
    @Select
    @Nullable
    TagTable findByName(String name);

    @Select
    @Nullable
    TagTable findByNameForUpdate(String name);
    
    @Insert
    int insert(TagTable tagTable);
    
    @Update
    int update(TagTable tagTable);
}
