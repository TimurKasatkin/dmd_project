package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.model.Journal;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
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
        return null;
    }
}
