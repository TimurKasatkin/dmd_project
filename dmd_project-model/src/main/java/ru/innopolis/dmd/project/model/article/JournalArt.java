package ru.innopolis.dmd.project.model.article;

import ru.innopolis.dmd.project.model.ArticleJournal;
import ru.innopolis.dmd.project.model.enums.ArticleType;

/**
 * Created by timur on 15.10.15.
 */
public class JournalArt extends Article {

    //Not always fetched
    private ArticleJournal journalLink;

    public JournalArt() {
        publtype = ArticleType.JOURNAL_ARTICLE;
    }

    public JournalArt(String title, String url, Integer year) {
        this(null, title, url, year);
    }

    public JournalArt(Long id, String title, String url, Integer year) {
        super(id, title, ArticleType.JOURNAL_ARTICLE, url, year);
    }

    public ArticleJournal getJournalLink() {
        return journalLink;
    }

    public void setJournalLink(ArticleJournal journalLink) {
        this.journalLink = journalLink;
    }
}
