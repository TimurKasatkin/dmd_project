package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.Keyword;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.logic.ConditionType.LIKE_INSENSITIVE;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("keywordDao")
public class KeywordDaoImpl extends AbstractDaoImpl<Keyword, Long> implements KeywordDao {
    public KeywordDaoImpl() {
        super(Keyword.class);
    }

    @Override
    public Long save(Keyword entity) {
        return null;
    }

    @Override
    public void update(Keyword entity) {

    }

    @Override
    public List<Keyword> findBySomeFieldLike(String value) {
        List result = stream(RowUtils.addTablePrefix(tableName, new Select(tableName, new Condition("word", LIKE_INSENSITIVE, value)).loadAll()))
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row)).collect(Collectors.toList());
        return result;
    }
}
