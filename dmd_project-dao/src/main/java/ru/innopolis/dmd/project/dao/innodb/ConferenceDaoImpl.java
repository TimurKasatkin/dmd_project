package ru.innopolis.dmd.project.dao.innodb;

import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ConferenceDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.Conference;

import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.logic.ConditionType.LIKE_INSENSITIVE;
import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
@Repository("conferenceDao")
public class ConferenceDaoImpl extends AbstractDaoImpl<Conference, Long> implements ConferenceDao {

    public ConferenceDaoImpl() {
        super(Conference.class);
    }

    public static void main(String[] args) {
        ConferenceDaoImpl conferenceDao = new ConferenceDaoImpl();
        List<Conference> learning = conferenceDao.findBySomeFieldLike("learning");
        System.out.println(learning.size());
    }

    @Override
    public Long save(Conference entity) {
        return null;
    }

    @Override
    public void update(Conference entity) {

    }

    @Override
    public List<Conference> findBySomeFieldLike(String value) {
        List result = stream(RowUtils.addTablePrefix(tableName, new Select(tableName, new Condition("name", LIKE_INSENSITIVE, value)).loadAll()))
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row)).collect(Collectors.toList());
        return result;
    }
}
