package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.UserDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.Row;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.logic.ConditionType;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.User;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {
    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        List<Row> rows = RowUtils.addTablePrefix(tableName, new Select(tableName, new Condition("login", ConditionType.EQUALS, login)).loadAll());
        User user = rows.size() > 0 ? InnoDBEntityMappingUtils.extractEntity(entityClass, rows.get(0)) : null;
        return user;
    }

    @Override
    public User findByLoginIgnoringCase(String login) {
        return null;
    }

    @Override
    public Long save(User entity) {
//        new Insert(new Row(
//                map(entry("id", entity.getId()),
//                        entry("login",entity.getLogin()),
//                        )
//        ), tableName).execute();
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public List<User> findBySomeFieldLike(String value) {
        return null;
    }
}
