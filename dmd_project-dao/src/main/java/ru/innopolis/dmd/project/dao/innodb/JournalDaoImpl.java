package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.Journal;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.logic.ConditionType.LIKE_INSENSITIVE;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("journalDao")
public class JournalDaoImpl extends AbstractDaoImpl<Journal, Long> implements JournalDao {
    public JournalDaoImpl() {
        super(Journal.class);
    }

    @Override
    public Long save(Journal entity) {
        return null;
    }

    @Override
    public void update(Journal entity) {

    }

    @Override
    public List<Journal> findBySomeFieldLike(String value) {
        List result = stream(RowUtils.addTablePrefix(tableName, new Select(tableName, new Condition("name", LIKE_INSENSITIVE, value)).loadAll()))
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row)).collect(Collectors.toList());
        return result;
    }
}
