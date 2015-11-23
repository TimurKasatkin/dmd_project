package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.logic.ConditionType.LIKE_INSENSITIVE;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("articleDao")
public class ArticleDaoImpl extends AbstractDaoImpl<Article, Long> implements ArticleDao {

    public ArticleDaoImpl() {
        super(Article.class);
    }

    public static void main(String[] args) {
        List<Article> title = new ArticleDaoImpl().findBySomeFieldLike("learning");
        System.out.println(title.size());
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
        return null;
    }

    @Override
    public void update(Article entity) {

    }

    @Override
    public List<Article> findBySomeFieldLike(String value) {
        List result = stream(RowUtils.addTablePrefix(tableName, new Select(tableName, new Condition("title", LIKE_INSENSITIVE, value)).loadAll()))
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row)).collect(Collectors.toList());
        return result;
    }
}
