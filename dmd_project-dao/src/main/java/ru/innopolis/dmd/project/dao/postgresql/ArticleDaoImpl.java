package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.model.ArticleJournal;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;
import ru.innopolis.dmd.project.model.enums.ArticleType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;
import static ru.innopolis.dmd.project.dao.util.EntityMapper.extractEntity;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.alias;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.fieldsStr;

/**
 * Created by timur on 15.10.15.
 */
@Repository("articleDao")
public class ArticleDaoImpl extends AbstractDaoImpl<Article, Long> implements ArticleDao {

    public final static String INSERT_SQL =
            "INSERT INTO articles(title, publtype, url, \"year\") VALUES (?,?,?,?)";

    @Autowired
    public ArticleDaoImpl(DataSource dataSource) {
        super(Article.class, dataSource);
    }

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl(testDataSource);
        List<JournalArt> allJournalArticles = articleDao.findAllJournalArticles();
        System.out.println("journal articles: " + allJournalArticles);
        List<ConferenceArt> allConferenceArticles = articleDao.findAllConferenceArticles();
        System.out.println("conference articles: " + allConferenceArticles);
        List<Article> all = articleDao.findAll();
        System.out.println("all articles: " + all);
        long count = articleDao.count();
        System.out.println("count: " + count);

        List<Article> articlesBy = articleDao.findBy("year", 1221);
        System.out.println("by year = 1221: " + articlesBy);

        List<Article> sortBy = articleDao.findAllAndSortBy("year", true);
        System.out.println("Sorted by year:" + sortBy
                .stream().map(a -> a.getTitle() + " " + a.getYear()).collect(Collectors.toList()));
    }

    @Override
    public List<JournalArt> findAllJournalArticles() {
        String ajAl = alias("article_journal");
        String journalAl = alias("journals");
        String sql = format("SELECT {0}, {1}, {2} " +
                "FROM articles {3} " +
                "JOIN article_journal {4} ON {3}.id = {4}.article_id " +
                "JOIN journals {5} ON {5}.id = {4}.journal_id;",
                        /*0*/tableFieldsStr,
                        /*1*/fieldsStr(ArticleJournal.class),
                        /*2*/fieldsStr(Journal.class),
                        /*3*/alias, /*4*/ajAl, /*5*/journalAl);
        return jdbcTemplate.query(sql, (rs, rowNum) -> extractEntity(JournalArt.class, rs));
    }

    @Override
    public List<ConferenceArt> findAllConferenceArticles() {
        return jdbcTemplate.query(
                "SELECT " + tableFieldsStr + ", " +
                        fieldsStr(Conference.class) + " " +
                        "FROM articles a " +
                        "JOIN article_conference ac ON a.id = ac.article_id " +
                        "JOIN conferences conf ON conf.id = ac.conference_id;"
                , (rs, rowNum) -> extractEntity(ConferenceArt.class, rs));
    }

    @Override
    public List<Article> findByKeywords(boolean shouldIncludeAll, String... keywords) {
        throw new NotImplementedException();
    }

    @Override
    public List<Article> findByAuthor(Author author) {
        String artAl = alias("articles");
        String artAuthAl = alias("article_author");
        String sql = "SELECT " + tableFieldsStr + " " +
                "FROM articles a " +
                "JOIN article_author aa ON a.id = aa.article_id " +
                "JOIN authors auth ON auth.id = aa.author_id " +
                "WHERE auth.first_name=? AND last_name=?;";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> extractEntity(Article.class, rs),
                author.getFirstName(), author.getLastName());
    }

    @Override
    public List<JournalArt> findByJournal(Journal journal) {
        throw new NotImplementedException();
    }

    @Override
    public List<ConferenceArt> findByConference(Conference conference) {
        throw new NotImplementedException();
    }

    @Override
    public Long save(Article entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps
                    = con.prepareStatement(INSERT_SQL, new String[]{"id"});
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
    //TODO think about related entities
    public void update(Article entity) {
        jdbcTemplate.update("UPDATE articles " +
                        "SET id=?,title=?,publtype=?,url=?,\"year\"=? " +
                        "WHERE id=?;",
                entity.getId(),
                entity.getTitle(),
                entity.getPubltype().getName(),
                entity.getUrl(),
                entity.getYear(),
                entity.getId());
    }
}
