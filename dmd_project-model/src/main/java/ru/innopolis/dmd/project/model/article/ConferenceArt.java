package ru.innopolis.dmd.project.model.article;

import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.enums.ArticleType;

/**
 * Created by timur on 15.10.15.
 */
public class ConferenceArt extends Article {

    //Not always fetched
    private Conference conference;

    public ConferenceArt() {
        setPubltype(ArticleType.CONFERENCE_ARTICLE);
    }

    public ConferenceArt(String title, String url, Integer year) {
        this(null, title, url, year);
    }

    public ConferenceArt(Long id, String title, String url, Integer year) {
        super(id, title, ArticleType.CONFERENCE_ARTICLE, url, year);
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
