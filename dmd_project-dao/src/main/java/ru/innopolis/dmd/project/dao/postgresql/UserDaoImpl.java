package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.UserDao;
import ru.innopolis.dmd.project.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;

import static java.text.MessageFormat.format;

/**
 * Created by timur on 16.10.15.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {

    public final static String INSERT_SQL =
            "INSERT INTO users(\"login\", \"password\", email) VALUES (?,?,?)";

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        super(User.class, dataSource);
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl(testDataSource);
        System.out.println(userDao.count());
        User user = userDao.findByLogin("dwqqw");
        user = userDao.findByLoginIgnoringCase("Dwqqw");
        System.out.println(user.getLogin());
    }

    @Override
    public User findByLogin(String login) {
        return jdbcTemplate.queryForObject("SELECT " + tableFieldsStr + " " +
                "FROM users u WHERE u.login=?", rowMapper(), login);
    }

    @Override
    public User findByLoginIgnoringCase(String login) {
        return jdbcTemplate.queryForObject(format("SELECT {0} FROM {1} {2} WHERE {2}.login ~* ''^{3}$''",
                tableFieldsStr, tableName, alias, login), rowMapper());
    }

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT " + tableFieldsStr + " " +
                "FROM users u WHERE u.email=?", rowMapper(), email);
    }

    @Override
    public Long save(User entity) {
        throw new NotImplementedException();
    }

    @Override
    public void update(User entity) {
        throw new NotImplementedException();
    }
}
