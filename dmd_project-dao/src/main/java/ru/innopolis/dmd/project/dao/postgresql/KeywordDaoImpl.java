package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.model.Keyword;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Repository("keywordDao")
public class KeywordDaoImpl extends AbstractDaoImpl<Keyword, Long> implements KeywordDao {

    @Autowired
    public KeywordDaoImpl(DataSource dataSource) {
        super(Keyword.class, dataSource);
    }

    @Override
    public Long save(Keyword entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO keywords (word) VALUES (?)", new String[]{"id"});
            ps.setString(1, entity.getWord());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Keyword entity) {
        jdbcTemplate.update("UPDATE keywords SET id=?,word=? WHERE id=?",
                entity.getId(),
                entity.getWord(),
                entity.getId());
    }

    @Override
    public List<Keyword> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM keywords k " +
                        "WHERE k.word ~* ?"
                , rowMapper(), value));
    }

}
