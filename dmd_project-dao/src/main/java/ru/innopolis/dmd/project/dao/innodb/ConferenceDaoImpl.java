package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.ConferenceDao;
import ru.innopolis.dmd.project.model.Conference;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class ConferenceDaoImpl extends AbstractDaoImpl<Conference, Long> implements ConferenceDao {

    public ConferenceDaoImpl() {
        super(Conference.class);
    }

    @Override
    public Long save(Conference entity) {
        return super.save(entity);
    }

    @Override
    public void update(Conference entity) {
        super.update(entity);
    }

    @Override
    public List<Conference> findBySomeFieldLike(String value) {
        return null;
    }
}
