package entrance.infrastructure.database.book;

import entrance.infrastructure.database.view.BookItemView;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface BookTableDao {

    @Select
    BookTable find(long itemId);

    @Select
    List<BookItemView> findNotTaggedBooks(int minRank, int maxRank);

    @Select
    List<BookItemView> findTaggedBooksByTagIdList(List<Long> tagIdList, int minRank, int maxRank);
    
    @Insert
    int insert(BookTable bookTable);

    @Delete
    int delete(BookTable bookTable);
}
