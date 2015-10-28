package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.model.Author;

import javax.sql.DataSource;

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
        return null;
    }

    @Override
    public void update(Author entity) {

    }

}
