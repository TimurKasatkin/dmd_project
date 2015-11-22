package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.AuthorDao;
import ru.innopolis.dmd.project.model.Author;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class AuthorDaoImpl extends AbstractDaoImpl<Author, Long> implements AuthorDao {

    public AuthorDaoImpl() {
        super(Author.class);
    }

    @Override
    public Long save(Author entity) {
        return super.save(entity);
    }

    @Override
    public void update(Author entity) {
        super.update(entity);
    }

    @Override
    public List<Author> findBySomeFieldLike(String value) {
//        new Select(new Scan(tableName),C)
        return null;
    }
}
