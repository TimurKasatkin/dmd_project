package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.Article;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public class Keyword extends LongIdEntity {

    private String word;

    private List<Article> articles;

    public Keyword() {
    }

    public Keyword(String word) {
        this(null, word);
    }

    public Keyword(Long id, String word) {
        super(id);
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return word.equals(keyword.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
