package entrance.infrastructure.database;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "TAG")
public class TagTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @Column(name = "FILTER_WORD")
    public String filterWord;

    @Override
    public String toString() {
        return "TagTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filterWord='" + filterWord + '\'' +
                '}';
    }
}
