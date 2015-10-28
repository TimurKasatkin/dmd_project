package ru.innopolis.dmd.project.dao.util;

import ru.innopolis.dmd.project.model.*;
import ru.innopolis.dmd.project.model.article.Article;
import ru.innopolis.dmd.project.model.article.ConferenceArt;
import ru.innopolis.dmd.project.model.article.JournalArt;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * @author Timur Kasatkin
 * @date 23.10.15.
 * @email aronwest001@gmail.com
 */
public interface Constants {

    Map<Class<? extends IdentifiedEntity>, String> ENTITY_TABLE_NAME =
            Stream.of(
                    new SimpleEntry<>(Article.class, "articles"),
                    new SimpleEntry<>(JournalArt.class, "articles"),
                    new SimpleEntry<>(ConferenceArt.class, "articles"),
                    new SimpleEntry<>(Conference.class, "conferences"),
                    new SimpleEntry<>(Author.class, "authors"),
                    new SimpleEntry<>(ArticleJournal.class, "article_journal"),
                    new SimpleEntry<>(Journal.class, "journals"),
                    new SimpleEntry<>(Keyword.class, "keywords"),
                    new SimpleEntry<>(User.class, "users")
            ).collect(toMap(Entry::getKey, Entry::getValue));
    Map<String, String> DEFAULT_TABLE_ALIAS =
            Stream.of(
                    new SimpleEntry<>("article_author", "aa"),
                    new SimpleEntry<>("article_conference", "ac"),
                    new SimpleEntry<>("article_journal", "aj"),
                    new SimpleEntry<>("article_keyword", "ak"),
                    new SimpleEntry<>("articles", "a"),
                    new SimpleEntry<>("authors", "auth"),
                    new SimpleEntry<>("conferences", "c"),
                    new SimpleEntry<>("journals", "j"),
                    new SimpleEntry<>("keywords", "k"),
                    new SimpleEntry<>("users", "u")
            ).collect(toMap(Entry::getKey, Entry::getValue));
    Map<String, String[]> TABLE_FIELDS =
            Stream.of(
                    new SimpleEntry<>("article_author",
                            new String[]{"article_id", "author_id"}),
                    new SimpleEntry<>("article_conference",
                            new String[]{"article_id", "conference_id"}),
                    new SimpleEntry<>("article_journal",
                            new String[]{"article_id",
                                    "journal_id", "volume", "number"}),
                    new SimpleEntry<>("article_keyword",
                            new String[]{"article_id", "keyword_id"}),
                    new SimpleEntry<>("articles",
                            new String[]{"id", "title", "publtype", "url", "year"}),
                    new SimpleEntry<>("authors",
                            new String[]{"id", "last_name", "first_name"}),
                    new SimpleEntry<>("conferences",
                            new String[]{"id", "name"}),
                    new SimpleEntry<>("journals",
                            new String[]{"id", "name"}),
                    new SimpleEntry<>("keywords",
                            new String[]{"id", "word"}),
                    new SimpleEntry<>("users",
                            new String[]{"id", "login", "password", "email", "role"})
            ).collect(toMap(Entry::getKey, Entry::getValue));
    String POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/dmd_semester_task";
    String POSTGRESQL_LOGIN = "postgres";
    String POSTGRESQL_PASSWORD = "postgres";


}
