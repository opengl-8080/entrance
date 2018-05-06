package entrance.infrastructure.database.book;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "BOOK")
public class BookTable {
    @Id
    public Long itemId;
    public String path;
    public String name;

    @Override
    public String toString() {
        return "BookTable{" +
                "itemId=" + itemId +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
