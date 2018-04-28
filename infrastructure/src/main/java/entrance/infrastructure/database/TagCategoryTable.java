package entrance.infrastructure.database;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "TAG_CATEGORY")
public class TagCategoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;

    @Override
    public String toString() {
        return "TagCategoryTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
