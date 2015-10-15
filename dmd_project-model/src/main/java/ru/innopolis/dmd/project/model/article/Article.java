package ru.innopolis.dmd.project.model.article;

import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Keyword;
import ru.innopolis.dmd.project.model.LongIdEntity;
import ru.innopolis.dmd.project.model.enums.ArticleType;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public abstract class Article extends LongIdEntity {

    private String title;

    private ArticleType publtype;

    private String url;

    private Integer year;

    private List<Author> authors;

    private List<Keyword> keywords;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article = (Article) o;

        return title.equals(article.title);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleType getPubltype() {
        return publtype;
    }

    public void setPubltype(ArticleType publtype) {
        this.publtype = publtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }
}
