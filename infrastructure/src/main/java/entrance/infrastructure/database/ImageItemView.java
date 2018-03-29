package entrance.infrastructure.database;

import lombok.ToString;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@ToString
public class ImageItemView {
    @Id
    public Long id;
    @Column(name = "IS_NOT_CATEGORIZED")
    public boolean notCategorized;
    @Column(name = "REGISTERED_DATETIME")
    public LocalDateTime registeredDateTime;
    public String path;
}
