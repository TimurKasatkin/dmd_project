package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.UserDao;
import ru.innopolis.dmd.project.model.User;
import ru.innopolis.dmd.project.service.UserService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

    @Override
    public boolean exists(String login) {
        return false;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public User findByLoginIgnoreCase(String login) {
        return null;
    }
}
