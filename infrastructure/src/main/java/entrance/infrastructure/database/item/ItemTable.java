package entrance.infrastructure.database.item;

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
public class ItemTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "REGISTERED_DATETIME")
    public LocalDateTime registeredDateTime;
    
    public int rank;

    @Override
    public String toString() {
        return "ItemTable{" +
                "id=" + id +
                ", registeredDateTime=" + registeredDateTime +
                ", rank=" + rank +
                '}';
    }
}
