package ru.innopolis.dmd.project.dao.innodb.util;

import ru.innopolis.dmd.project.innodb.Row;
import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Timur Kasatkin
 * @date 22.11.15.
 * @email aronwest001@gmail.com
 */
public class EntityMappingUtils {

    public static <E extends IdentifiedEntity> E extractEntity(Class<? extends IdentifiedEntity> entClass, Row row) {
        try {
            return (E) EntityMappingUtils.class
                    .getDeclaredMethod("extract" + entClass.getSimpleName(), Row.class)
                    .invoke(null, row);
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("There is no mapping method for such entity");
        }
    }

    private static ConferenceArt extractConferenceArt(Row row) {
        return (ConferenceArt) extractArticle(row);
    }

    private static JournalArt extractJournalArt(Row row) {
        return (JournalArt) extractArticle(row);
    }

    private static ArticleJournal extractArticleJournal(Row row) {

        return null;
    }

    private static Article extractArticle(Row row) {

        return null;
    }

    private static Author extractAuthor(Row row) {

        return null;
    }

    private static Conference extractConference(Row row) {

        return null;
    }

    private static Journal extractJournal(Row row) {

        return null;
    }

    private static Keyword extractKeyword(Row row) {

        return null;
    }

    private static User extractUser(Row row) {

        return null;
    }


}
