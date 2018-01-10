package entrance.infrastructure.database;

import lombok.ToString;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "IMAGE")
@ToString
public class ImageTable {
    @Id
    public Long itemId;
    public String path;
}
