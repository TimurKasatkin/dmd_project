package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.Article;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public class Keyword {

    private String word;

    private List<Article> articles;

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
