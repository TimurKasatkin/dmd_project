package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.model.Keyword;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
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
        throw new NotImplementedException();
    }

    @Override
    public void update(Keyword entity) {
        throw new NotImplementedException();
    }

    @Override
    public List<Keyword> findBySomeFieldLike(String value) {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " " +
                "FROM keywords k " +
                "WHERE k.word ~* ?"
                , rowMapper(), value));
    }

}
