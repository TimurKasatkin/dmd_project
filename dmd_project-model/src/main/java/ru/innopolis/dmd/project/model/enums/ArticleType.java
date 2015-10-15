package ru.innopolis.dmd.project.model.enums;

/**
 * Created by timur on 15.10.15.
 */
public enum ArticleType {

    JOURNAL_ARTICLE("journal_article"), CONFERENCE_ARTICLE("conference_article");

    private final String name;

    ArticleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
