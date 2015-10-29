package ru.innopolis.dmd.project.dao;

import ru.innopolis.dmd.project.model.User;

/**
 * Created by timur on 15.10.15.
 */
public interface UserDao extends AbstractDao<User, Long> {

    User findByLogin(String login);

    User findByLoginIgnoringCase(String login);

    User findByEmail(String email);

}
