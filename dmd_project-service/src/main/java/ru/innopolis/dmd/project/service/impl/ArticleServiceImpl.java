package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.service.ArticleService;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class ArticleServiceImpl extends AbstractServiceImpl<Article, Long> implements ArticleService {

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        super(articleDao);
    }
}
