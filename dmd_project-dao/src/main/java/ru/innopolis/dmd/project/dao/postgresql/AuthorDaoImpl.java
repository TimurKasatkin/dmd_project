package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.model.Author;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Repository("authorDao")
public class AuthorDaoImpl extends AbstractDaoImpl<Author, Long> implements AuthorDao {

    @Autowired
    public AuthorDaoImpl(DataSource dataSource) {
        super(Author.class, dataSource);
    }

    @Override
    public Long save(Author entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps
                    = con.prepareStatement("INSERT INTO authors (first_name, last_name) VALUES (?,?)", new String[]{"id"});
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Author entity) {
        jdbcTemplate.update("UPDATE authors " +
                        "SET id=?,first_name=?,last_name=? " +
                        "WHERE id=?",
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getId());
    }

    @Override
    public List<Author> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                        "FROM authors auth " +
                        "WHERE auth.first_name ~* ? OR auth.last_name ~* ?;"
                , rowMapper(), value, value));
    }

}
