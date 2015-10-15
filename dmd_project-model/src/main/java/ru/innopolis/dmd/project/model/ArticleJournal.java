package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.JournalArticle;

import java.io.Serializable;

/**
 * Created by timur on 15.10.15.
 */
public class ArticleJournal implements Serializable {

    private JournalArticle article;

    private Journal journal;

    private String volume;

    private String number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleJournal that = (ArticleJournal) o;
        return article.equals(that.article) && journal.equals(that.journal);
    }

    @Override
    public int hashCode() {
        int result = article.hashCode();
        result = 31 * result + journal.hashCode();
        return result;
    }

    public JournalArticle getArticle() {
        return article;
    }

    public void setArticle(JournalArticle article) {
        this.article = article;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
