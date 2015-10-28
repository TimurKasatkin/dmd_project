package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.Article;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public class Author extends LongIdEntity {

    private String firstName;

    private String lastName;

    private List<Article> articles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author author = (Author) o;
        if (firstName != null ? !firstName.equals(author.firstName)
                : author.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(author.lastName)
                : author.lastName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
