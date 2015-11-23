package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.Row;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.logic.LogicalOperation;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.Author;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.logic.ConditionType.LIKE_INSENSITIVE;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.list;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("authorDao")
public class AuthorDaoImpl extends AbstractDaoImpl<Author, Long> implements AuthorDao {

    public AuthorDaoImpl() {
        super(Author.class);
    }

    public static void main(String[] args) {
        AuthorDaoImpl authorDao = new AuthorDaoImpl();
        List<Author> res = authorDao.findBySomeFieldLike("john");
        System.out.println();
    }

    @Override
    public Long save(Author entity) {
        return null;
    }

    @Override
    public void update(Author entity) {

    }

    @Override
    public List<Author> findBySomeFieldLike(String value) {
        Select select = new Select(tableName, new Condition("first_name", LIKE_INSENSITIVE, list(value),
                LogicalOperation.OR, new Condition("last_name", LIKE_INSENSITIVE, list(value))));
        List<Row> rows = select.loadAll();
        List result = stream(RowUtils.addTablePrefix(tableName, rows))
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row)).collect(Collectors.toList());
        return result;
    }
}
