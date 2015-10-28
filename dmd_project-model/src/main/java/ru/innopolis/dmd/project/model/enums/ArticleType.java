package ru.innopolis.dmd.project.model.enums;

import java.util.stream.Stream;

/**
 * Created by timur on 15.10.15.
 */
public enum ArticleType {

    JOURNAL_ARTICLE("journal_article"), CONFERENCE_ARTICLE("conference_article");

    private final String name;

    ArticleType(String name) {
        this.name = name;
    }

    public static ArticleType byName(String name) {
        return Stream.of(values())
                .filter(artT -> artT.name.equals(name))
                .findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }
}
