package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ImageTableDao {
    
    @Select
    ImageTable find(long itemId);
    
    @Select
    List<ImageItemView> findNotCategorizedImages();
    
    @Select
    List<ImageItemView> findCategorizedImagesByTagIdList(List<Long> tagIdList);
    
    @Insert
    int insert(ImageTable imageTable);
}
