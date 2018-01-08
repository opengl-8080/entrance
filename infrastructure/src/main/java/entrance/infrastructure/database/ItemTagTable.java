package entrance.infrastructure.database;

import lombok.ToString;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "ITEM_TAG")
@ToString
public class ItemTagTable {
    @Id
    Long itemId;
    @Id
    Long tagId;
    @Column(name = "IS_MAIN")
    boolean main;
}
