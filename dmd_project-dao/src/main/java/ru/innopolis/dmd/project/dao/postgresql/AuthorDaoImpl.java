package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.model.Author;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
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
        throw new NotImplementedException();
    }

    @Override
    public void update(Author entity) {
        throw new NotImplementedException();
    }

    @Override
    public List<Author> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM authors auth " +
                "WHERE auth.first_name ~* ? OR auth.last_name ~* ?;"
                , rowMapper(), value, value));
    }

}
