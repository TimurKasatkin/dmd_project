package ru.innopolis.dmd.project.dao.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static java.text.MessageFormat.format;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.alias;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.fieldsStr;

/**
 * @author Timur Kasatkin
 * @date 30.10.15.
 * @email aronwest001@gmail.com
 */
@SuppressWarnings("unchecked")
public class FetchUtils {

    public static <E extends IdentifiedEntity> void proxy(Class<E> entityClass, E entity, JdbcTemplate jdbcTemplate) {
        try {
            FetchUtils.class.getDeclaredMethod("proxy" + entityClass.getSimpleName(), entityClass, JdbcTemplate.class)
                    .invoke(null, entity, jdbcTemplate);
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void proxyArticle(Article article, JdbcTemplate jdbcTemplate) {
        if (article != null) {
            if (article.getAuthors() == null) {
                article.setAuthors((List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[]{List.class},
                        new InvocationHandler() {
                            private List<Author> authors;

                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (authors == null) {
                                    System.out.println("Proxy ARTICLE: authors");
                                    authors = jdbcTemplate.query(
                                            format("SELECT {0} FROM authors {1} " +
                                                    "JOIN article_author {2} ON {1}.id = {2}.author_id " +
                                                    "WHERE {2}.article_id=?",
                                                    fieldsStr(Author.class), alias(Author.class), alias("article_author")),
                                            (rs, rowNum) -> EntityMappingUtils.extractEntity(Author.class, rs),
                                            article.getId());
                                    if (authors != null)
                                        authors.forEach(author -> proxyAuthor(author, jdbcTemplate));
                                }
                                return method.invoke(authors, args);
                            }
                        }));
            }
            if (article.getKeywords() == null) {
                article.setKeywords((List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[]{List.class},
                        new InvocationHandler() {
                            private List<Keyword> keywords;

                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (keywords == null) {
                                    System.out.println("Proxy ARTICLE: keywords");
                                    keywords = jdbcTemplate.query(
                                            format("SELECT {0} FROM keywords {1} " +
                                                            "JOIN article_keyword {2} ON {1}.id = {2}.keyword_id " +
                                                            "WHERE {2}.article_id=?",
                                                    fieldsStr(Keyword.class), alias(Keyword.class), alias("article_keyword")),
                                            (rs, rowNum) -> EntityMappingUtils.extractEntity(Keyword.class, rs),
                                            article.getId());
                                    if (keywords != null)
                                        keywords.forEach(keyword -> proxyKeyword(keyword, jdbcTemplate));
                                }
                                return method.invoke(keywords, args);
                            }
                        }));
            }
        }
    }

    public static void proxyJournalArt(JournalArt journalArt, JdbcTemplate jdbcTemplate) {
        if (journalArt != null) {
            proxyArticle(journalArt, jdbcTemplate);
            journalArt.setJournalLink((ArticleJournal) Enhancer.create(ArticleJournal.class, (LazyLoader) () -> {
                System.out.println("Proxy JOURNAL ARTICLE: journal link");
                ArticleJournal articleJournal = jdbcTemplate.query(
                        format("SELECT {0} FROM article_journal {2} " +
                                        "JOIN articles {1} ON {1}.id = {2}.article_id " +
                                        "WHERE {1}.id=?",
                                fieldsStr(ArticleJournal.class), alias(Article.class), alias(ArticleJournal.class)),
                        rs -> rs.next() ? EntityMappingUtils.extractEntity(ArticleJournal.class, rs) : null,
                        journalArt.getId());
                if (articleJournal != null)
                    articleJournal.setArticle(journalArt);
                proxyArticleJournal(articleJournal, jdbcTemplate);
                return articleJournal;
            }));
        }
    }

    public static void proxyConferenceArt(ConferenceArt conferenceArt, JdbcTemplate jdbcTemplate) {
        if (conferenceArt != null) {
            proxyArticle(conferenceArt, jdbcTemplate);
            conferenceArt.setConference((Conference) Enhancer.create(Conference.class, (LazyLoader) () -> {
                System.out.println("Proxy CONFERENCE ARTICLE: conferences");
                Conference conference = jdbcTemplate.query(
                        format("SELECT {0} " +
                                        "FROM conferences {1} " +
                                        "JOIN article_conference {2} ON {1}.id = {2}.conference_id " +
                                        "WHERE {2}.article_id=?",
                                fieldsStr(Conference.class), alias(Conference.class), alias("article_conference")),
                        rs -> rs.next() ? EntityMappingUtils.extractEntity(Conference.class, rs) : null,
                        conferenceArt.getId());
                proxyConference(conference, jdbcTemplate);
                return conference;
            }));
        }
    }

    public static void proxyArticleJournal(ArticleJournal articleJournal, JdbcTemplate jdbcTemplate) {
        if (articleJournal != null) {
            if (articleJournal.getArticle() == null) {
                articleJournal.setArticle((JournalArt) Enhancer.create(JournalArt.class, (LazyLoader) () -> {
                    System.out.println("Proxy ARTICLE_JOURNAL: article");
                    return jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM articles a  " +
                                    "JOIN article_journal aj ON a.id = aj.article_id " +
                                    "WHERE a.id=?",
                            rs -> rs.next() ? EntityMappingUtils.extractEntity(JournalArt.class, rs) : null,
                            articleJournal.getId().getArticleId());
                }));
            }
            if (articleJournal.getJournal() == null) {
                articleJournal.setJournal((Journal) Enhancer.create(Journal.class, (LazyLoader) () -> {
                    System.out.println("Proxy ARTICLE_JOURNAL: journal");
                    Journal journal = jdbcTemplate.query("SELECT " + fieldsStr(Journal.class) + " " +
                                    "FROM journals j " +
                                    "JOIN article_journal aj ON j.id = aj.journal_id " +
                                    "WHERE aj.article_id=?",
                            rs -> rs.next() ? EntityMappingUtils.extractEntity(Journal.class, rs) : null,
                            articleJournal.getArticle().getId());
                    proxyJournal(journal, jdbcTemplate);
                    return journal;
                }));
            }
        }
    }

    public static void proxyAuthor(Author author, JdbcTemplate jdbcTemplate) {
        if (author != null) {
            author.setArticles((List) Enhancer.create(List.class, (LazyLoader) () -> {
                System.out.println("Proxy AUTHOR");
                return jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                "FROM article_author aa ON auth.id = aa.author_id " +
                                "JOIN articles a ON a.id = aa.article_id " +
                                "WHERE aa.author_id=?",
                        (rs, rowNum) -> EntityMappingUtils.extractEntity(Article.class, rs)
                        , author.getId());
            }));
        }
    }

    public static void proxyKeyword(Keyword keyword, JdbcTemplate jdbcTemplate) {
        if (keyword != null) {
            if (keyword.getArticles() == null) {
                keyword.setArticles((List) Enhancer.create(List.class, (LazyLoader) () -> {
                    System.out.println("Proxy KEYWORD");
                    return jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM article_keyword ak ON k.id = ak.keyword_id " +
                                    "JOIN articles a ON a.id = ak.article_id " +
                                    "WHERE ak.keyword_id=?",
                            (rs, rowNum) -> EntityMappingUtils.extractEntity(Article.class, rs),
                            keyword.getId());
                }));
            }
        }
    }

    public static void proxyJournal(Journal journal, JdbcTemplate jdbcTemplate) {
        if (journal != null) {
            if (journal.getArticleJournals() == null) {
                journal.setArticleJournals((List) Enhancer.create(List.class, (LazyLoader) () -> {
                    System.out.println("Proxy JOURNAL: articleJournals");
                    List<ArticleJournal> articleJournals = jdbcTemplate.query("SELECT " + fieldsStr(ArticleJournal.class) + " " +
                                    "FROM journals j " +
                                    "JOIN article_journal aj ON j.id = aj.journal_id " +
                                    "WHERE j.id=?",
                            (rs, rowNum) -> EntityMappingUtils.extractEntity(ArticleJournal.class, rs),
                            journal.getId());
                    if (articleJournals != null) {
                        articleJournals.forEach(aj -> {
                            aj.setJournal(journal);
                            proxyArticleJournal(aj, jdbcTemplate);
                        });
                    }
                    return articleJournals;
                }));
            }
        }
    }

    public static void proxyConference(Conference conference, JdbcTemplate jdbcTemplate) {
        if (conference != null) {
            if (conference.getArticles() == null) {
                conference.setArticles((List) Enhancer.create(List.class, (LazyLoader) () -> {
                    System.out.println("Proxy CONFERENCE: articles");
                    return jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM article_conference ac ON c.id = ac.conference_id " +
                                    "JOIN articles a ON a.id = ac.article_id " +
                                    "WHERE ac.conference_id=?",
                            (rs, rowNum) -> EntityMappingUtils.extractEntity(ConferenceArt.class, rs),
                            conference.getId());
                }));
            }
        }
    }


}
