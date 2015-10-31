package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.model.Journal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.util.List;

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
        throw new NotImplementedException();
    }

    @Override
    public void update(Journal entity) {
        throw new NotImplementedException();
    }

    @Override
    public List<Journal> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM journals j " +
                "WHERE j.name ~* ?"
                , rowMapper(), value));
    }

}
