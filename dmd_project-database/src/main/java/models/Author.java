package models;

/**
 * Created by Филипп on 25.09.2015.
 */
public class Author {
    String firstname;
    String lastname;

    public void parse(String name){
        String[] str = name.split(" ");
        firstname = str[0];
        lastname = str[str.length - 1];
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
