package entrance.infrastructure.database;

import lombok.ToString;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

@ToString
@Entity(naming = NamingType.LENIENT_SNAKE_UPPER_CASE)
@Table(name = "TAG_KANA")
public class TagKanaTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "TAG_ID")
    Long tagId;
    String kana;
}
