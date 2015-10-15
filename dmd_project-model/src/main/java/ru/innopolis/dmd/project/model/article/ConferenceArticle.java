package ru.innopolis.dmd.project.model.article;

import ru.innopolis.dmd.project.model.Conference;

/**
 * Created by timur on 15.10.15.
 */
public class ConferenceArticle extends Article {

    private Conference conference;

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
