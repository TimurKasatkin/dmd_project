package ru.innopolis.dmd.project.dao;

import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public interface ArticleDao extends AbstractDao<Article, Long> {

    List<Article> findByKeywords(boolean shouldIncludeAll, String... keywords);

    List<Article> findByKeyword(String keyword);

    List<Article> findByAuthor(Author author);

    List<JournalArt> findByJournal(Journal journal);

    List<ConferenceArt> findByConference(Conference conference);

}
