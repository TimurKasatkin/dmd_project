package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.KeywordDao;
import ru.innopolis.dmd.project.model.Keyword;
import ru.innopolis.dmd.project.service.KeywordService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class KeywordServiceImpl extends AbstractServiceImpl<Keyword, Long> implements KeywordService {

    @Autowired
    public KeywordServiceImpl(KeywordDao keywordDao) {
        super(keywordDao);
    }
}
