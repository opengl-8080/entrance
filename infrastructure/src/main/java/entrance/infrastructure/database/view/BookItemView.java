package entrance.infrastructure.database.view;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
public class BookItemView {
    @Id
    public Long id;
    @Column(name = "REGISTERED_DATETIME")
    public LocalDateTime registeredDateTime;
    public String path;
    public String name;
    public int rank;
}
