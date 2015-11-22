package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.model.Keyword;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class KeywordDaoImpl extends AbstractDaoImpl<Keyword, Long> implements KeywordDao {
    public KeywordDaoImpl() {
        super(Keyword.class);
    }

    @Override
    public Long save(Keyword entity) {
        return null;
    }

    @Override
    public void update(Keyword entity) {

    }

    @Override
    public List<Keyword> findBySomeFieldLike(String value) {
        return null;
    }
}
