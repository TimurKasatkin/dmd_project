package ru.innopolis.dmd.project.dao.postgresql.utils;

import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;
import ru.innopolis.dmd.project.model.enums.ArticleType;
import ru.innopolis.dmd.project.model.enums.UserRole;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Timur Kasatkin
 * @date 15.10.15.
 * @email aronwest001@gmail.com
 */
public class EntityMappingUtils {

    @SuppressWarnings("unchecked")
    public static <E extends IdentifiedEntity> E extractEntity(Class<? extends IdentifiedEntity> entClass, ResultSet rs) {
        try {
            return (E) EntityMappingUtils.class
                    .getDeclaredMethod("extract" + entClass.getSimpleName(), ResultSet.class).invoke(null, rs);
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("There is no mapping method for such entity");
        }
    }

    private static ConferenceArt extractConferenceArt(ResultSet rs) {
        return (ConferenceArt) extractArticle(rs);
    }

    private static JournalArt extractJournalArt(ResultSet rs) {
        return (JournalArt) extractArticle(rs);
    }

    private static ArticleJournal extractArticleJournal(ResultSet rs) {
        try {
            return new ArticleJournal(ResultSetUtils.extractIfContains(Long.class, "articlejournal_article_id", rs),
                    ResultSetUtils.extractIfContains(Long.class, "articlejournal_journal_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "articlejournal_volume", rs),
                    ResultSetUtils.extractIfContains(String.class, "articlejournal_number", rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Article extractArticle(ResultSet rs) {
        try {
            if (ResultSetUtils.contains(rs, "article_publtype")) {
                ArticleType publtype = ArticleType
                        .byName(rs.getString("article_publtype"));
                Article article = null;
                switch (publtype) {
                    case JOURNAL_ARTICLE:
                        article = new JournalArt();
                        JournalArt journalArt = (JournalArt) article;
                        Journal journal = extractJournal(rs);
                        ArticleJournal journalLink = new ArticleJournal(journalArt,
                                journal,
                                ResultSetUtils.extractIfContains(String.class, "articlejournal_volume", rs),
                                ResultSetUtils.extractIfContains(String.class, "articlejournal_number", rs));
                        if (journal != null) {
                            journal.setArticleJournals(new LinkedList<>());
                            journal.getArticleJournals().add(journalLink);
                        }
                        journalArt.setJournalLink(journalLink);
                        break;
                    case CONFERENCE_ARTICLE:
                        article = new ConferenceArt();
                        ConferenceArt conferenceArt = (ConferenceArt) article;
                        conferenceArt.setConference(extractConference(rs));
                        break;
                }
                article.setId(ResultSetUtils.extractIfContains(Long.class, "article_id", rs));
                article.setTitle(ResultSetUtils.extractIfContains(String.class, "article_title", rs));
                article.setUrl(ResultSetUtils.extractIfContains(String.class, "article_url", rs));
                article.setYear(ResultSetUtils.extractIfContains(Integer.class, "article_year", rs));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Author extractAuthor(ResultSet rs) {
        try {
            return new Author(ResultSetUtils.extractIfContains(Long.class, "author_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "author_first_name", rs),
                    ResultSetUtils.extractIfContains(String.class, "author_last_name", rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Conference extractConference(ResultSet rs) {
        try {
            return new Conference(ResultSetUtils.extractIfContains(Long.class, "conference_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "conference_name", rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Journal extractJournal(ResultSet rs) {
        try {
            return new Journal(ResultSetUtils.extractIfContains(Long.class, "journal_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "journal_name", rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Keyword extractKeyword(ResultSet rs) {
        try {
            return new Keyword(ResultSetUtils.extractIfContains(Long.class, "keyword_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "keyword_word", rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static User extractUser(ResultSet rs) {
        try {
            String roleName = ResultSetUtils.extractIfContains(String.class, "user_role", rs);
            return new User(ResultSetUtils.extractIfContains(Long.class, "user_id", rs),
                    ResultSetUtils.extractIfContains(String.class, "user_login", rs),
                    ResultSetUtils.extractIfContains(String.class, "user_password", rs),
                    ResultSetUtils.extractIfContains(String.class, "user_email", rs),
                    roleName != null ? UserRole.valueOf(roleName) : null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
