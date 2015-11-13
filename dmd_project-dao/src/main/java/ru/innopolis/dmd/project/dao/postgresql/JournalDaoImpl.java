package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.JournalDao;
import ru.innopolis.dmd.project.model.Journal;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO journals (name) VALUES (?)", new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Journal entity) {
        jdbcTemplate.update("UPDATE journals SET id=?,name=? WHERE id=?",
                entity.getId(),
                entity.getName(),
                entity.getId());
    }

    @Override
    public List<Journal> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM journals j " +
                        "WHERE j.name ~* ?"
                , rowMapper(), value));
    }

}
