package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ConferenceDao;
import ru.innopolis.dmd.project.model.Conference;

import javax.sql.DataSource;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Repository("conferenceDao")
public class ConferenceDaoImpl extends AbstractDaoImpl<Conference, Long> implements ConferenceDao {

    @Autowired
    public ConferenceDaoImpl(DataSource dataSource) {
        super(Conference.class, dataSource);
    }

    @Override
    public Long save(Conference entity) {
        return null;
    }

    @Override
    public void update(Conference entity) {

    }

}
