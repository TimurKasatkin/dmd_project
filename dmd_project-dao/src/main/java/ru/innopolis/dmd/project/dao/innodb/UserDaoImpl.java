package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.UserDao;
import ru.innopolis.dmd.project.model.User;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class UserDaoImpl extends AbstractDaoImpl<User, Long> implements UserDao {
    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public User findByLoginIgnoringCase(String login) {
        return null;
    }

    @Override
    public Long save(User entity) {
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
