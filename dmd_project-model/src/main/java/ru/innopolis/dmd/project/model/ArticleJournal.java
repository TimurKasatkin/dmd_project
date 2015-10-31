package ru.innopolis.dmd.project.model;

import ru.innopolis.dmd.project.model.article.JournalArt;

import java.io.Serializable;

/**
 * Created by timur on 15.10.15.
 */
public class ArticleJournal implements IdentifiedEntity<ArticleJournal.ArticleJournalId> {

    private ArticleJournalId id;

    private JournalArt article;

    private Journal journal;

    private String volume;

    private String number;

    public ArticleJournal() {
    }

    public ArticleJournal(Long articleId, Long journalId, String volume, String number) {
        this(volume, number);
        id = new ArticleJournalId(articleId, journalId);
    }

    public ArticleJournal(String volume, String number) {
        this.volume = volume;
        this.number = number;
    }

    public ArticleJournal(JournalArt article, Journal journal,
                          String volume, String number) {
        this.article = article;
        this.journal = journal;
        this.volume = volume;
        this.number = number;
    }

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

    public JournalArt getArticle() {
        return article;
    }

    public void setArticle(JournalArt article) {
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

    @Override
    public ArticleJournalId getId() {
        return id;
    }

    @Override
    public void setId(ArticleJournalId id) {
        this.id = id;
    }

    public static class ArticleJournalId implements Serializable {

        private Long articleId;

        private Long journalId;

        public ArticleJournalId() {
        }

        public ArticleJournalId(Long articleId, Long journalId) {
            this.articleId = articleId;
            this.journalId = journalId;
        }

        public Long getArticleId() {
            return articleId;
        }

        public void setArticleId(Long articleId) {
            this.articleId = articleId;
        }

        public Long getJournalId() {
            return journalId;
        }

        public void setJournalId(Long journalId) {
            this.journalId = journalId;
        }
    }

}
