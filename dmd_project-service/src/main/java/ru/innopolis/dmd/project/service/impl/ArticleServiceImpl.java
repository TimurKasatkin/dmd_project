package ru.innopolis.dmd.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.dmd.project.dao.ArticleDao;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;
import ru.innopolis.dmd.project.service.ArticleService;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
@Service
public class ArticleServiceImpl extends AbstractServiceImpl<Article, Long> implements ArticleService {


    private ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        super(articleDao);
        this.articleDao = articleDao;
    }

    @Override
    public List<JournalArt> findByJournal(Journal journal) {
        return articleDao.findByJournal(journal);
    }

    @Override
    public List<ConferenceArt> findByConference(Conference conference) {
        return articleDao.findByConference(conference);
    }
}
