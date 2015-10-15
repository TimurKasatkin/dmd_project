package ru.innopolis.dmd.project.model.article;

import ru.innopolis.dmd.project.model.ArticleJournal;

/**
 * Created by timur on 15.10.15.
 */
public class JournalArticle extends Article {

    private ArticleJournal journalLink;

    public ArticleJournal getJournalLink() {
        return journalLink;
    }

    public void setJournalLink(ArticleJournal journalLink) {
        this.journalLink = journalLink;
    }
}
