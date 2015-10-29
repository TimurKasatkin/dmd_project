package ru.innopolis.dmd.project.dao;

import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public interface ArticleDao extends AbstractDao<Article, Long> {

    @Override
    default List<Article> findAll() {
        LinkedList<Article> articles = new LinkedList<>();
        articles.addAll(findAllJournalArticles());
        articles.addAll(findAllConferenceArticles());
        return articles;
    }

    List<JournalArt> findAllJournalArticles();

    List<ConferenceArt> findAllConferenceArticles();

    default List<Article> findByKeywords(boolean shouldIncludeAll, List<String> keywords) {
        return findByKeywords(shouldIncludeAll, keywords.toArray(new String[keywords.size()]));
    }

    List<Article> findByKeywords(boolean shouldIncludeAll, String... keywords);

    List<Article> findByAuthor(Author author);

    List<JournalArt> findByJournal(Journal journal);

    List<ConferenceArt> findByConference(Conference conference);

}
