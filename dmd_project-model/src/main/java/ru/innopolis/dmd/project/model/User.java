package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.enums.UserRole;

/**
 * Created by timur on 15.10.15.
 */
public class User extends LongIdEntity {

    private String login;

    private String password;

    private String email;

    private UserRole role;

    public User() {
    }

    public User(String login, String password, String email, UserRole role) {
        this(null, login, password, email, role);
    }

    public User(Long id, String login, String password, String email, UserRole role) {
        super(id);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role == null ?/*give default role*/null : role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
