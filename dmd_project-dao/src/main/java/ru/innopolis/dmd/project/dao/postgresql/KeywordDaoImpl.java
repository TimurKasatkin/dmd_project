package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.model.Keyword;

import javax.sql.DataSource;

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
        return null;
    }

    @Override
    public void update(Keyword entity) {

    }

}
