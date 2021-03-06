package entrance.infrastructure.database.image;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "IMAGE")
public class ImageTable {
    @Id
    public Long itemId;
    public String path;
    public int width;
    public int height;

    @Override
    public String toString() {
        return "ImageTable{" +
                "itemId=" + itemId +
                ", path='" + path + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
