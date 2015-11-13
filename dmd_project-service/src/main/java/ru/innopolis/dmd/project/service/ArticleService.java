package ru.innopolis.dmd.project.service;

import ru.innopolis.dmd.project.model.Author;
import ru.innopolis.dmd.project.model.Conference;
import ru.innopolis.dmd.project.model.Journal;
import ru.innopolis.dmd.project.model.Keyword;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Timur Kasatkin
 * @date 27.10.15.
 * @email aronwest001@gmail.com
 */
public interface ArticleService extends AbstractService<Article, Long> {

    List<JournalArt> findByJournal(Journal journal);

    List<ConferenceArt> findByConference(Conference conference);

    List<Article> findByAuthor(Author author);

    List<Article> findByKeyword(String keyword);

    default List<Article> findByKeyword(Keyword keyword) {
        return findByKeyword(keyword.getWord());
    }

    List<Article> findByKeywords(boolean shouldIncludeAll, String... keyword);

    default List<Article> findByKeywords(boolean shouldIncludeAll, Keyword... keywords) {
        List<String> keywordsList = Stream.of(keywords).map(Keyword::getWord).collect(Collectors.toList());
        return findByKeywords(shouldIncludeAll, keywordsList.toArray(new String[keywords.length]));
    }

}
