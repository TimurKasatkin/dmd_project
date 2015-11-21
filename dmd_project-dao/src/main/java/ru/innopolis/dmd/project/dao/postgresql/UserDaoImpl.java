package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.UserDao;
import ru.innopolis.dmd.project.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Created by timur on 16.10.15.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {

    private final static String INSERT_SQL =
            "INSERT INTO users(login, password, email) VALUES (?,?,?)";

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        super(User.class, dataSource);
    }

    @Override
    public User findByLogin(String login) {
        return jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM users u WHERE u.login=?", resExtractor(), login);
    }

    @Override
    public User findByLoginIgnoringCase(String login) {
        return jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {2}.login ~* ''^{3}$''",
                tableFieldsStr, tableName, alias, login), resExtractor());
    }

    @Override
    public Long save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users " +
                        "SET id=?,login=?,password=?,email=?,role=? " +
                        "WHERE id=?;",
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getRole().name(),
                entity.getId()
        );
    }

    @Override
    public List<User> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM users u " +
                "WHERE u.login ~* ? OR u.email ~* ?"
                , rowMapper(), value, value));
    }
}
