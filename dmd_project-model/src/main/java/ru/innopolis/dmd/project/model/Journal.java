package ru.innopolis.dmd.project.model;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public class Journal extends LongIdEntity {

    private String name;

    private List<ArticleJournal> articleJournals;

    public Journal() {
    }

    public Journal(String name) {
        this(null, name);
    }

    public Journal(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Journal journal = (Journal) o;
        return name.equals(journal.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleJournal> getArticleJournals() {
        return articleJournals;
    }

    public void setArticleJournals(List<ArticleJournal> articleJournals) {
        this.articleJournals = articleJournals;
    }
}
