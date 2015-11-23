package ru.innopolis.dmd.project.dao.innodb.util;

import ru.innopolis.dmd.project.innodb.Row;
import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;
import ru.innopolis.dmd.project.model.enums.ArticleType;
import ru.innopolis.dmd.project.model.enums.UserRole;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

/**
 * @author Timur Kasatkin
 * @date 22.11.15.
 * @email aronwest001@gmail.com
 */
public class InnoDBEntityMappingUtils {

    public static <E extends IdentifiedEntity> E extractEntity(Class<? extends IdentifiedEntity> entClass, Row row) {
        try {
            return (E) InnoDBEntityMappingUtils.class
                    .getDeclaredMethod("extract" + entClass.getSimpleName(), Row.class)
                    .invoke(null, row);
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static ConferenceArt extractConferenceArt(Row row) {
        return (ConferenceArt) extractArticle(row);
    }

    private static JournalArt extractJournalArt(Row row) {
        return (JournalArt) extractArticle(row);
    }

    private static ArticleJournal extractArticleJournal(Row row) {
        ArticleJournal articleJournal = new ArticleJournal(
                InnoDBEntityMappingUtils.<Long>extractIfExists("article_journal_article_id", row),
                InnoDBEntityMappingUtils.<Long>extractIfExists("article_journal_journal_id", row),
                extractIfExists("article_journal_volume", row),
                extractIfExists("article_journal_number", row));
        return articleJournal;
    }

    private static Article extractArticle(Row row) {
        ArticleType publtype = ArticleType.byName((String) row.v("articles_publtype"));
        Article article = null;
        switch (publtype) {
            case JOURNAL_ARTICLE:
                article = new JournalArt();
                break;
            case CONFERENCE_ARTICLE:
                article = new ConferenceArt();
                break;
        }
        article.setKeywords(new LinkedList<>());
        article.setAuthors(new LinkedList<>());
        article.setId(extractIfExists("articles_id", row));
        article.setTitle(extractIfExists("articles_title", row));
        article.setUrl(extractIfExists("articles_url", row));
        Comparable articles_year = row.v("articles_year");
        article.setYear((Integer) articles_year);
        return article;
    }

    private static <T> T extractIfExists(String fieldName, Row row) {
        if (row.has(fieldName)) {
            Comparable v = row.v(fieldName);
            if (v instanceof Integer) {
                return (T) (((Long) ((Integer) v).longValue()));
            }
            return (T) v;
        } else return null;
    }

    private static Author extractAuthor(Row row) {
        Author author = new Author(extractIfExists("authors_id", row),
                extractIfExists("authors_first_name", row),
                extractIfExists("authors_last_name", row));
        author.setArticles(new LinkedList<>());
        return author;
    }

    private static Conference extractConference(Row row) {
        Conference conference = new Conference(extractIfExists("conferences_id", row),
                extractIfExists("conferences_name", row));
        conference.setArticles(new LinkedList<>());
        return conference;
    }

    private static Journal extractJournal(Row row) {
        Journal journal = new Journal(extractIfExists("journals_id", row),
                extractIfExists("journals_name", row));
        journal.setArticleJournals(new LinkedList<>());
        return journal;
    }

    private static Keyword extractKeyword(Row row) {
        Keyword keyword = new Keyword(extractIfExists("keywords_id", row),
                extractIfExists("keywords_word", row));
        keyword.setArticles(new LinkedList<>());
        return keyword;
    }

    private static User extractUser(Row row) {
        Long id = extractIfExists("users_id", row);
        User user = new User(id,
                extractIfExists("users_login", row),
                extractIfExists("users_password", row),
                extractIfExists("users_email", row),
                UserRole.valueOf(extractIfExists("users_role", row)));
        return user;
    }


}
