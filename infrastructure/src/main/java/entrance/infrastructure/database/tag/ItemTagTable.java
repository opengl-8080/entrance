package entrance.infrastructure.database.tag;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "ITEM_TAG")
public class ItemTagTable {
    @Id
    public Long itemId;
    @Id
    public Long tagId;

    @Override
    public String toString() {
        return "ItemTagTable{" +
                "itemId=" + itemId +
                ", tagId=" + tagId +
                '}';
    }
}
