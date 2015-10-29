package ru.innopolis.dmd.project.service;

import ru.innopolis.dmd.project.model.User;

/**
 * @author Timur Kasatkin
 * @date 27.10.15.
 * @email aronwest001@gmail.com
 */
public interface UserService extends AbstractService<User, Long> {

    boolean exists(String login);

    User findByLogin(String login);

    User findByLoginIgnoreCase(String login);

}
