package entrance.infrastructure.database;

import lombok.ToString;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "ITEM")
@ToString
public class ItemTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "IS_NOT_CATEGORIZED")
    public boolean notCategorized;
    @Column(name = "REGISTERED_DATETIME")
    public LocalDateTime registeredDateTime;
}