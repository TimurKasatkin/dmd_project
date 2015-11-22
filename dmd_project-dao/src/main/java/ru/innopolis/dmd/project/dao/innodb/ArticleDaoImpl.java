package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class ArticleDaoImpl extends AbstractDaoImpl<Article, Long> implements ArticleDao {

    public ArticleDaoImpl() {
        super(Article.class);
    }

    @Override
    public List<Article> findByKeywords(boolean shouldIncludeAll, String... keywords) {
        return null;
    }

    @Override
    public List<Article> findByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<Article> findByAuthor(Author author) {
        return null;
    }

    @Override
    public List<JournalArt> findByJournal(Journal journal) {
        return null;
    }

    @Override
    public List<ConferenceArt> findByConference(Conference conference) {
        return null;
    }

    @Override
    public Long save(Article entity) {
        return super.save(entity);
    }

    @Override
    public void update(Article entity) {
        super.update(entity);
    }
}
