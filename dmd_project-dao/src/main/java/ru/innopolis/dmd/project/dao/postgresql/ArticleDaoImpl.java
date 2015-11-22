package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.dao.postgresql.utils.EntityMappingUtils;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;
import ru.innopolis.dmd.project.model.enums.ArticleType;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by timur on 15.10.15.
 */
@Repository("articleDao")
public class ArticleDaoImpl extends AbstractDaoImpl<Article, Long> implements ArticleDao {

    private final static String INSERT_SQL =
            "INSERT INTO articles(title, publtype, url, year) VALUES (?,?,?,?)";

    @Autowired
    public ArticleDaoImpl(DataSource dataSource) {
        super(Article.class, dataSource);
    }

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl(testDataSource);
//        List<JournalArt> allJournalArticles = articleDao.findAllJournalArticles();
//        System.out.println("journal articles: " + allJournalArticles);
//
//        List<ConferenceArt> allConferenceArticles = articleDao.findAllConferenceArticles();
//        System.out.println("conference articles: " + allConferenceArticles);
//
//        List<Article> all = articleDao.findAll();
//        System.out.println("all articles: " + all);
//
//        for (Article article : all) {
//            System.out.println("==================================");
//            System.out.println(article.getTitle());
//            if (article instanceof JournalArt) {
//                ArticleJournal journalLink = ((JournalArt) article).getJournalLink();
//                System.out.println(journalLink.getNumber());
//                System.out.println(journalLink.getVolume());
//                System.out.println(journalLink.getJournal().getName());
//            }
//            if (article instanceof ConferenceArt) {
//                ConferenceArt conferenceArt = (ConferenceArt) article;
//                System.out.println(conferenceArt.getConference().getName());
//            }
//            List<Keyword> keywords = article.getKeywords();
//            System.out.println("KEYWORDS:");
//            keywords.forEach(keyword -> System.out.println(keyword.getWord()));
//            List<Author> authors = article.getAuthors();
//            System.out.println("AUTHORS:");
//            authors.forEach(author -> {
//                System.out.println("  " + author.getFirstName() + " " + author.getLastName());
//            });
//            System.out.println("==================================");
//        }


        List<Article> bySomeFieldLike = articleDao.findBySomeFieldLike("title");

        bySomeFieldLike.forEach(article -> System.out.println(article.getTitle()));

//        List<Article> articlesBy = articleDao.findBy("year", 1221);
//        System.out.println("by year = 1221: " + articlesBy);
//
//        List<Article> sortBy = articleDao.findAllAndSortBy("year", true);
//        System.out.println("Sorted by year:" + sortBy
//                .stream().map(a -> a.getTitle() + " " + a.getYear()).collect(Collectors.toList()));
    }

    @Override
    public List<Article> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM articles a " +
                        "WHERE a.title ~* ?"
                , rowMapper(), value));
    }

    @Override
    public List<Article> findByKeywords(boolean shouldIncludeAll, String... keywords) {
        String sql;
        String keywordsStr = Stream.of(keywords)
                .map(s -> "'" + s.replace("'", "''") + "'").collect(Collectors.joining());
        if (shouldIncludeAll)
            sql = "SELECT DISTINCT " + tableFieldsStr + " " +
                    "FROM articles a JOIN article_keyword ak " +
                    "ON a.id = ak.article_id " +
                    "WHERE ak.article_id IN (SELECT ak.article_id AS id " +
                    /*                   */"FROM article_keyword ak " +
                    /*                   */"WHERE ak.keyword_id IN (SELECT k.id " +
                    /*                                           */"FROM keywords k " +
                    /*                                           */"WHERE k.word IN (" + keywordsStr + ")) " +
                    /*                                           */"GROUP BY ak.article_id " +
                    /*                                           */"HAVING count(*) = " + keywords.length + ");";
        else {
            sql = "SELECT DISTINCT " + tableFieldsStr + " " +
                    "FROM articles AS a " +
                    "JOIN article_keyword ak ON a.id = ak.article_id " +
                    "WHERE ak.keyword_id IN (SELECT id " +
                    /*                   */"FROM keywords " +
                    /*                   */"WHERE word IN (" + keywordsStr + "))";
        }
        return proxy(jdbcTemplate.query(sql, rowMapper()));
    }

    @Override
    public List<Article> findByKeyword(String keyword) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM keywords k " +
                "JOIN article_keyword ak ON k.id = ak.keyword_id " +
                "JOIN articles a ON ak.article_id = a.id " +
                "WHERE k.word=?", rowMapper(), keyword));
    }

    @Override
    public List<Article> findByAuthor(Author author) {
        String sql = "SELECT " + tableFieldsStr + " " +
                "FROM authors auth " +
                "JOIN article_author aa ON auth.id = aa.author_id " +
                "JOIN articles a ON aa.article_id = a.id " +
                "WHERE auth.id=?;";
        return proxy(jdbcTemplate.query(sql, rowMapper(), author.getId()));
    }

    @Override
    public List<JournalArt> findByJournal(Journal journal) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM journals j " +
                        "JOIN article_journal aj ON j.id = aj.journal_id " +
                        "JOIN articles a ON a.id = aj.article_id " +
                        "WHERE j.id=?",
                (rs, rowNum) -> EntityMappingUtils.extractEntity(JournalArt.class, rs),
                journal.getId()));
    }

    @Override
    public List<ConferenceArt> findByConference(Conference conference) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM conferences c " +
                        "JOIN article_conference ac ON c.id = ac.conference_id " +
                        "JOIN articles a ON ac.article_id = a.id " +
                        "WHERE c.id=?",
                (rs, rowNum) -> EntityMappingUtils.extractEntity(ConferenceArt.class, rs),
                conference.getId()));
    }

    @Override
    public Long save(Article entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, entity.getTitle());
            ArticleType publtype = entity.getPubltype();
            ps.setString(2, publtype != null ? publtype.getName() : null);
            ps.setString(3, entity.getUrl());
            ps.setInt(4, entity.getYear());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Article entity) {
        jdbcTemplate.update("UPDATE articles " +
                        "SET id=?,title=?,publtype=?,url=?,year=? " +
                        "WHERE id=?;",
                entity.getId(),
                entity.getTitle(),
                entity.getPubltype().getName(),
                entity.getUrl(),
                entity.getYear(),
                entity.getId());
    }
}
