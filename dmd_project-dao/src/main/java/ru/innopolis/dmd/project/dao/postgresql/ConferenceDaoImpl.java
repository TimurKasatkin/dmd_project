package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.ConferenceDao;
import ru.innopolis.dmd.project.model.Conference;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO conferences (name) VALUES (?)", new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Conference entity) {
        jdbcTemplate.update("UPDATE conferences SET id=?,name=? WHERE id=?",
                entity.getId(),
                entity.getName(),
                entity.getId());
    }

    @Override
    public List<Conference> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM conferences c " +
                        "WHERE c.name ~* ?"
                , rowMapper(), value));
    }

}
