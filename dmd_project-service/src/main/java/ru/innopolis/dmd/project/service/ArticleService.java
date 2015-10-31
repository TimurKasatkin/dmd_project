package ru.innopolis.dmd.project.service;

import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 27.10.15.
 * @email aronwest001@gmail.com
 */
public interface ArticleService extends AbstractService<Article, Long> {

    List<JournalArt> findByJournal(Journal journal);

    List<ConferenceArt> findByConference(Conference conference);

}
