package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.ConferenceArt;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public class Conference extends LongIdEntity {

    private String name;

    private List<ConferenceArt> articles;

    public Conference() {
    }

    public Conference(String name) {
        this(null, name);
    }

    public Conference(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Conference that = (Conference) o;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public List<ConferenceArt> getArticles() {
        return articles;
    }

    public void setArticles(List<ConferenceArt> articles) {
        this.articles = articles;
    }
}
