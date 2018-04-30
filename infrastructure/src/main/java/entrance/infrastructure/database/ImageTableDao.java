package entrance.infrastructure.database;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
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
    List<ImageItemView> findNotTaggedImages(int minRank, int maxRank);
    
    @Select
    List<ImageItemView> findTaggedImagesByTagIdList(List<Long> tagIdList, int minRank, int maxRank);
    
    @Select
    List<ImageItemView> findWallpapers(int minRank, int maxRank);
    
    @Insert
    int insert(ImageTable imageTable);
    
    @Delete
    int delete(ImageTable imageTable);
}
