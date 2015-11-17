package ru.innopolis.dmd.project.dao;

import ru.innopolis.dmd.project.model.User;

/**
 * @author Timur Kasatkin
 * @date 15.10.15.
 * @email aronwest001@gmail.com
 */
public interface UserDao extends AbstractDao<User, Long> {

    User findByLogin(String login);

    User findByLoginIgnoringCase(String login);

}
