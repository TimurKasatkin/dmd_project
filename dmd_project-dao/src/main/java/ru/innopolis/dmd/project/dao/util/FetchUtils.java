package ru.innopolis.dmd.project.dao.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static ru.innopolis.dmd.project.dao.util.SQLUtils.alias;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.fieldsStr;

/**
 * @author Timur Kasatkin
 * @date 30.10.15.
 * @email aronwest001@gmail.com
 */
@SuppressWarnings("unchecked")
//TODO implement fetching by proxy
public class FetchUtils {

    public static <E extends IdentifiedEntity> void proxy(Class<E> entityClass, E entity, JdbcTemplate jdbcTemplate) {
        try {
            FetchUtils.class.getDeclaredMethod("proxy" + entityClass.getSimpleName(), entityClass, JdbcTemplate.class)
                    .invoke(null, entity, jdbcTemplate);
//            return (E) EntityMapper.class
//                    .getDeclaredMethod("extract" + entityClass.getSimpleName(), ResultSet.class).invoke(null, rs);
        } catch (NullPointerException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
//        if (entity instanceof JournalArt) {
//            proxyJournalArt((JournalArt) entity, jdbcTemplate);
//        } else if (entity instanceof ConferenceArt) {
//            proxy((ConferenceArt) entity, jdbcTemplate);
//        } else if (entity instanceof Article) {
//            proxyArticle((Article) entity, jdbcTemplate);
//        } else if (entity instanceof Author) {
//            proxy((Author) entity, jdbcTemplate);
//        } else if (entity instanceof Keyword) {
//            proxy((Keyword) entity, jdbcTemplate);
//        } else if (entity instanceof ArticleJournal) {
//            proxy((ArticleJournal) entity, jdbcTemplate);
//        } else if (entity instanceof Journal) {
//            proxy((Journal) entity, jdbcTemplate);
//        } else if (entity instanceof Conference) {
//            proxy((Conference) entity, jdbcTemplate);
//        }
    }

    public static void proxyArticle(Article article, JdbcTemplate jdbcTemplate) {
        if (article.getAuthors() == null) {
            article.setAuthors((List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[]{List.class},
                    new InvocationHandler() {
                        private List<Author> authors;

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (authors == null) {
                                String aa = alias("article_author");
                                String auth = alias(Author.class);
                                authors = jdbcTemplate.query("SELECT " + fieldsStr(Author.class) + " " +
                                                "FROM authors " + auth + " " +
                                                "JOIN article_author " + aa + " ON " + auth + ".id = " + aa + ".author_id " +
                                                "WHERE " + aa + ".article_id=?",
                                        (rs, rowNum) -> EntityMapper.extractEntity(Author.class, rs),
                                        article.getId());
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
                                String k = alias(Keyword.class);
                                String ak = alias("article_keyword");
                                keywords = jdbcTemplate.query("SELECT " + fieldsStr(Keyword.class) + " " +
                                                "FROM keywords " + k + " " +
                                                "JOIN article_keyword " + ak + " ON " + k + ".id = " + ak + ".keyword_id " +
                                                "WHERE " + ak + ".article_id=?",
                                        (rs, rowNum) -> EntityMapper.extractEntity(Keyword.class, rs),
                                        article.getId());
                                keywords.forEach(keyword -> proxyKeyword(keyword, jdbcTemplate));
                            }
                            return method.invoke(keywords, args);
                        }
                    }));
        }
    }

    public static void proxyJournalArt(JournalArt journalArt, JdbcTemplate jdbcTemplate) {
        proxyArticle(journalArt, jdbcTemplate);
        journalArt.setJournalLink((ArticleJournal) Enhancer.create(ArticleJournal.class, (LazyLoader) () -> {
//            System.out.println("Proxy journalArt");
            String a = alias(Article.class);
            String aj = alias(ArticleJournal.class);
            ArticleJournal articleJournal = jdbcTemplate.query("SELECT " + fieldsStr(ArticleJournal.class) +
                            " FROM article_journal " + aj + " " +
                            "JOIN articles " + a + " ON " + a + ".id = " + aj + ".article_id " +
                            "WHERE " + a + ".id=?",
                    (ResultSetExtractor<ArticleJournal>) rs -> rs.next() ?
                            EntityMapper.extractEntity(ArticleJournal.class, rs) : null,
                    journalArt.getId());
            articleJournal.setArticle(journalArt);
            proxyArticleJournal(articleJournal, jdbcTemplate);
            return articleJournal;
        }));
    }

    public static void proxyConferenceArt(ConferenceArt conferenceArt, JdbcTemplate jdbcTemplate) {
        proxyArticle(conferenceArt, jdbcTemplate);
        String c = alias(Conference.class);
        String ac = alias("article_conference");
        conferenceArt.setConference((Conference) Enhancer.create(Conference.class, (LazyLoader) () -> {
//            System.out.println("Proxy conferenceArt");
            Conference conference = jdbcTemplate.query("SELECT " + fieldsStr(Conference.class) +
                            " FROM conferences " + c + " " +
                            "JOIN article_conference " + ac + " ON " + c + ".id = " + ac + ".conference_id " +
                            "WHERE " + ac + ".article_id=?",
                    (ResultSetExtractor<Conference>) rs -> rs.next() ? EntityMapper.extractEntity(Conference.class, rs) : null,
                    conferenceArt.getId());
            proxyConference(conference, jdbcTemplate);
            return conference;
        }));
    }

    public static void proxyArticleJournal(ArticleJournal articleJournal, JdbcTemplate jdbcTemplate) {
        if (articleJournal.getArticle() == null) {
            articleJournal.setArticle((JournalArt) Enhancer.create(JournalArt.class, (LazyLoader) () ->
                    jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM articles a  " +
                                    "JOIN article_journal aj ON a.id = aj.article_id " +
                                    "WHERE a.id=?",
                            (ResultSetExtractor<JournalArt>) rs -> rs.next() ? EntityMapper.extractEntity(JournalArt.class, rs) : null,
                            articleJournal.getId().getArticleId())));
        }
        if (articleJournal.getJournal() == null) {
            articleJournal.setJournal((Journal) Enhancer.create(Journal.class, (LazyLoader) () -> {
                System.out.println("Proxy articleJournal");
                Journal journal = jdbcTemplate.query("SELECT " + fieldsStr(Journal.class) + " " +
                                "FROM journals j " +
                                "JOIN article_journal aj ON j.id = aj.journal_id " +
                                "WHERE aj.article_id=?",
                        (ResultSetExtractor<Journal>) rs -> rs.next() ? EntityMapper.extractEntity(Journal.class, rs) : null,
                        articleJournal.getArticle().getId());
                proxyJournal(journal, jdbcTemplate);
                return journal;
            }));
        }
    }

    public static void proxyAuthor(Author author, JdbcTemplate jdbcTemplate) {
        author.setArticles((List) Enhancer.create(List.class, (LazyLoader) () -> {
            System.out.println("Proxy author");
            return jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                            "FROM authors auth " +
                            "JOIN article_author aa ON auth.id = aa.author_id " +
                            "JOIN articles a ON a.id = aa.article_id " +
                            "WHERE auth.id=?",
                    (rs, rowNum) -> EntityMapper.extractEntity(Article.class, rs)
                    , author.getId());
        }));
    }

    public static void proxyKeyword(Keyword keyword, JdbcTemplate jdbcTemplate) {
        if (keyword.getArticles() == null) {
            keyword.setArticles((List) Enhancer.create(List.class, (LazyLoader) () ->
                    jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM keywords k " +
                                    "JOIN article_keyword ak ON k.id = ak.keyword_id " +
                                    "JOIN articles a ON a.id = ak.article_id " +
                                    "WHERE k.id=?",
                            (rs, rowNum) -> EntityMapper.extractEntity(Article.class, rs),
                            keyword.getId())));
        }
    }

    public static void proxyJournal(Journal journal, JdbcTemplate jdbcTemplate) {
        if (journal.getArticleJournals() == null) {
            journal.setArticleJournals((List) Enhancer.create(List.class, (LazyLoader) () -> {
                List<ArticleJournal> articleJournals = jdbcTemplate.query("SELECT " + fieldsStr(ArticleJournal.class) + " " +
                                "FROM journals j " +
                                "JOIN article_journal aj ON j.id = aj.journal_id " +
                                "WHERE j.id=?",
                        (rs, rowNum) -> EntityMapper.extractEntity(ArticleJournal.class, rs),
                        journal.getId());
                articleJournals.forEach(aj -> {
                    aj.setJournal(journal);
                    proxyArticleJournal(aj, jdbcTemplate);
                });

                return articleJournals;
            }));
        }
    }

    public static void proxyConference(Conference conference, JdbcTemplate jdbcTemplate) {
        if (conference.getArticles() == null) {
            conference.setArticles((List) Enhancer.create(List.class, (LazyLoader) () ->
                    jdbcTemplate.query("SELECT " + fieldsStr(Article.class) + " " +
                                    "FROM conferences c " +
                                    "JOIN article_conference ac ON c.id = ac.conference_id " +
                                    "JOIN articles a ON a.id = ac.article_id " +
                                    "WHERE c.id=?",
                            (rs, rowNum) -> EntityMapper.extractEntity(ConferenceArt.class, rs),
                            conference.getId())));
        }
    }


}
