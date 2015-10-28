package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.model.Journal;

import javax.sql.DataSource;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Repository("journalDao")
public class JournalDaoImpl extends AbstractDaoImpl<Journal, Long> implements JournalDao {

    @Autowired
    public JournalDaoImpl(DataSource dataSource) {
        super(Journal.class, dataSource);
    }

    @Override
    public Long save(Journal entity) {
        return null;
    }

    @Override
    public void update(Journal entity) {

    }

}
