package entrance.infrastructure.database.tag.category;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface TagCategoryTableDao {
    @Select
    @NotNull
    List<TagCategoryTable> findAll();

    @Select
    TagCategoryTable find(Long id);

    @Select
    @Nullable
    TagCategoryTable findByName(String name);

    @Select
    @Nullable
    TagCategoryTable findByNameForUpdate(String name);

    @Insert
    int insert(TagCategoryTable tagCategoryTable);

    @Update
    int update(TagCategoryTable tagCategoryTable);

    @Delete
    int delete(TagCategoryTable tagCategoryTable);
}
