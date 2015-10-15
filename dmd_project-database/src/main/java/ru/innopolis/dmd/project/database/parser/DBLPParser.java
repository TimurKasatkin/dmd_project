package ru.innopolis.dmd.project.database.parser;

import javafx.util.Pair;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static javax.xml.stream.XMLStreamConstants.*;

/**
 * Created by timur on 26.09.15.
 */
public class DBLPParser {

    /**
     * names of entities we want to process
     */
    public final static Set<String> entities =
            Stream.of("article", "inproceedings").collect(Collectors.toSet());
    private String dblpXmlPath;
    private ArticleService articleDao;
    private int count = 0;

    private DBLPParser(String dblpXmlPath, String dbUrl, String dbUserName, String dbPassword) {
        File file = new File(dblpXmlPath);
        if (!file.exists()) throw new IllegalArgumentException("invalid dblp xml path");
        this.dblpXmlPath = dblpXmlPath;
        this.articleDao = new DBLoadArticleService(dbUrl, dbUserName, dbPassword);
    }

    public DBLPParser(String dblpXmlPath) {
        File file = new File(dblpXmlPath);
        if (!file.exists()) throw new IllegalArgumentException("invalid dblp xml path");
        this.dblpXmlPath = dblpXmlPath;
        this.articleDao = new SQLFileLoadArticleService();
    }

    public static void parse(String dblpXmlPath, String dbUrl, String dbUserName, String dbPassword) {
        new DBLPParser(dblpXmlPath, dbUrl, dbUserName, dbPassword).parse();
    }

    public static void parse(String dblpXmlPath) {
        new DBLPParser(dblpXmlPath).parse();
    }

    public static void main(String[] args) throws Exception {
//        DBLPParser.parse("/home/timur/Documents/innopolis/dmd/semestr task/dblp.xml",
//                "jdbc:postgresql://localhost:5432/dmd_semester_task",
//                "postgres",
//                "postgres");
        DBLPParser.parse("/home/timur/Documents/innopolis/dmd/semestr task/dblp.xml");
    }

    private void addCounter() {
        count++;
        printCount();
    }

    private void printCount() {
        System.out.println("Parsed articles count: " + count);
    }

    private void processEntity(String entityName, XMLStreamReader reader) throws XMLStreamException {
        switch (entityName) {
            case "article":
                processArticle(reader, new JournalArticle());
                break;
            case "inproceedings":
                processArticle(reader, new ConferenceArticle());
                break;
        }
    }


    private void processArticle(XMLStreamReader reader, Article article) throws XMLStreamException {
        String currentTag = "";
        PROCESS:
        while (reader.hasNext()) {
            switch (/*event code*/reader.next()) {
                case START_ELEMENT:
                    currentTag = reader.getLocalName();
                    break;
                case CHARACTERS:
                    if (!currentTag.isEmpty())
                        processArticleValue(reader, currentTag, article);
                    currentTag = "";
                    break;
                case END_ELEMENT:
                    currentTag = "";
                    if (entities.contains(reader.getLocalName())) break PROCESS;
            }
        }
        if (articleDao.addArticle(article)) addCounter();
    }

    private void processArticleValue(XMLStreamReader reader, String currentTag, Article article) {
        String str = reader.getText().trim();
        if (!str.isEmpty()) {
            switch (currentTag) {
                case "title":
                    article.setTitleAndParseKeyword(str);
                case "ee":
                    article.url = str;
                    return;
                case "year":
                    article.year = Integer.parseInt(str);
                    return;
                case "author":
                    String[] split = str.split(" ");
                    article.authors.add(new Pair<>(split[0], split[split.length - 1]));
                    return;
            }
            if (article instanceof JournalArticle) {
                JournalArticle journalArticle = (JournalArticle) article;
                switch (currentTag) {
                    case "journal":
                        journalArticle.journal = str;
                        return;
                    case "volume":
                        journalArticle.volume = str;
                        return;
                    case "number":
                        journalArticle.number = str;
                        return;
                }
            }
            if (article instanceof ConferenceArticle) {
                ConferenceArticle conferenceArticle = (ConferenceArticle) article;
                switch (currentTag) {
                    case "booktitle":
                        conferenceArticle.conference = str;
                        return;
                }
            }
        }
    }

    private void parse() {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        try {
            XMLStreamReader reader = xmlInputFactory
                    .createXMLStreamReader(new FileInputStream(dblpXmlPath), "UTF-8");
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case START_ELEMENT:
                        String entityName = reader.getLocalName();
                        if (entities.contains(entityName))
                            processEntity(entityName, reader);
                        break;
                }
            }
            System.out.println("ALL PARSED");
            printCount();
            articleDao.loadAll();
            System.out.println("ALL LOADED");
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                articleDao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private interface ArticleService extends AutoCloseable {

        boolean addJournal(String name);

        boolean addArticle(Article article);

        boolean addAuthor(Pair<String, String> fullName);

        boolean addConference(String conferenceName);

        boolean addKeyword(String word);

        void loadAll();

    }

    private static abstract class Article {
        protected String title, publtype, url;

        //        String journal, volume, number;
        int year = 0;

        List<Pair<String, String>> authors = new LinkedList<>();

        List<String> keywords = new LinkedList<>();

        public void setTitleAndParseKeyword(String title) {
            this.title = title;
            keywords = Stream.of(title.split(" "))
                    .filter(s -> s.length() > 3).map(String::toLowerCase)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Article article = (Article) o;
            return year == article.year && !(title != null ? !title.equals(article.title) : article.title != null);
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + year;
            return result;
        }
    }

    private static class JournalArticle extends Article {
        String journal, volume, number;

        public JournalArticle() {
            publtype = "journal_article";
        }
    }

    private static class ConferenceArticle extends Article {
        String conference;

        public ConferenceArticle() {
            publtype = "conference_article";
        }
    }

    private static abstract class InMemoryArticleService implements ArticleService {

        protected List<String> journals;
        protected List<String> keyWords;
        protected List<String> conferences;
        protected List<Pair<String, String>> authors;
        protected Set<Article> articles;

        private ExecutorService executor;

        public InMemoryArticleService() {
            journals = new LinkedList<>();
            authors = new LinkedList<>();
            //article title and year
//            articles = new LinkedList<>();
            articles = new HashSet<>();
            keyWords = new LinkedList<>();
            conferences = new LinkedList<>();
            executor = Executors.newCachedThreadPool();
        }

        protected void clearLists() {
            articles.clear();
            authors.clear();
            journals.clear();
            keyWords.clear();
            conferences.clear();
        }

        @Override
        public boolean addArticle(Article article) {
            if (!articles.contains(article)) {
                List<Future<?>> futures = new LinkedList<>();
                if (article.authors != null) {
                    futures.add(executor.submit(() -> article.authors.forEach(this::addAuthor)));
                }
                if (article.keywords != null)
                    futures.add(executor.submit(() -> article.keywords.forEach(this::addKeyword)));
                if (article instanceof JournalArticle) {
                    JournalArticle journalArticle = (JournalArticle) article;
                    futures.add(executor.submit(() -> addJournal(journalArticle.journal)));
                }
                if (article instanceof ConferenceArticle) {
                    ConferenceArticle conferenceArticle = (ConferenceArticle) article;
                    futures.add(executor.submit(() -> addConference(conferenceArticle.conference)));
                }
                futures.forEach(future -> {
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
                articles.add(article);
                if (articles.size() >= 500) loadAll();
                return true;
            }
            return false;
        }

        @Override
        public boolean addJournal(String name) {
            return addIfNotContains(journals, name);
        }

        @Override
        public boolean addAuthor(Pair<String, String> fullName) {
            return addIfNotContains(authors, fullName);
        }

        @Override
        public boolean addConference(String conferenceName) {
            return addIfNotContains(conferences, conferenceName);
        }

        @Override
        public boolean addKeyword(String word) {
            return addIfNotContains(keyWords, word);
        }

        private <T> boolean addIfNotContains(Collection<T> coll, T value) {
            if (!coll.contains(value)) {
                coll.add(value);
                return true;
            }
            return false;
        }

        @Override
        public void close() throws Exception {
            executor.shutdown();
        }

        @Override
        public abstract void loadAll();
    }

    private static class SQLFileLoadArticleService extends InMemoryArticleService {

        static final String CONFERENCE_INSERT_FORMAT = "INSERT INTO conferences(name) VALUES(''{0}'');";

        static final String AUTHORS_INSERT_FORMAT = "INSERT INTO authors (first_name, last_name) VALUES (''{0}'',''{1}'');";

        static final String JOURNALS_INSERT_FORMAT = "INSERT INTO journals (name) VALUES (''{0}'');";

        static final String KEYWORDS_INSERT_FORMAT = "INSERT INTO keywords (word) VALUES (''{0}'');";

        static final String ARTICLES_INSERT_FORMAT = "INSERT INTO articles (title, publtype, url, year) " +
                "VALUES (''{0}'',''{1}'',''{2}'',{3});";

        static final String ARTICLE_AUTHOR_INSERT_FORMAT = "INSERT INTO article_author (article_id, author_id) " +
                "VALUES ((SELECT id FROM articles WHERE title = ''{0}'' AND year = {1})," +
                "(SELECT id FROM authors WHERE first_name = ''{2}'' AND last_name = ''{3}''));";
        static final String ARTICLE_CONFERENCE_INSERT_FORMAT = "INSERT INTO article_conference (article_id, conference_id) " +
                "VALUES ((SELECT id FROM articles WHERE title = ''{0}'' AND year = {1})," +
                "(SELECT id FROM conferences WHERE name = ''{2}''));";
        static final String ARTICLE_JOURNAL_INSERT_FORMAT = "INSERT INTO article_journal (article_id, journal_id, volume, number) " +
                "VALUES ((SELECT id FROM articles WHERE title = ''{0}'' AND year = {1})," +
                "(SELECT id FROM journals WHERE name = ''{2}''), ''{3}'', ''{4}'');";
        static final String ARTICLE_KEYWORD_INSERT_FORMAT = "INSERT INTO article_keyword (article_id, keyword_id) " +
                "VALUES ((SELECT id FROM articles WHERE title = ''{0}'' AND year = {1})," +
                "(SELECT id FROM keywords WHERE word = ''{2}''));";

        private int time = 0;

        private int lastJournalsInd = 0;
        private int lastKeyWordsInd = 0;
        private int lastConferencesInd = 0;
        private int lastAuthorsInd = 0;


        private void write(BufferedWriter writer, String insFormat, Object... args) throws IOException {
            for (int i = 0; i < args.length; i++)
                if (args[i] instanceof String)
                    args[i] = ((String) args[i]).replace("'", "''");
            writer.write(format(insFormat, args).replace("'null'", "NULL"));
            writer.newLine();
        }

        private void write(BufferedWriter writer, String insFormat, Collection<String> strings) throws IOException {
            for (String string : strings) write(writer, insFormat, string);
        }

        private void write(BufferedWriter writer, String insFormat, Article article, Object... otherArgs) throws IOException {
            Object[] args = new Object[otherArgs.length + 2];
            args[0] = article.title;
            args[1] = article.year + "";
            System.arraycopy(otherArgs, 0, args, 2, args.length - 2);
            write(writer, insFormat, args);
        }

        @Override
        public void loadAll() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.sql", time != 0))) {
                System.out.println("FLUSHING DATA TO FILE...");
                write(writer, "--CONFERENCE DATA");
                write(writer, CONFERENCE_INSERT_FORMAT, conferences.subList(lastConferencesInd, conferences.size()));
                write(writer, "--AUTHORS DATA");
                for (Pair<String, String> author : authors.subList(lastAuthorsInd, authors.size())) {
                    write(writer, AUTHORS_INSERT_FORMAT, author.getKey(), author.getValue());
                }
                write(writer, "--KEYWORDS DATA");
                write(writer, KEYWORDS_INSERT_FORMAT, keyWords.subList(lastKeyWordsInd, keyWords.size()));
                write(writer, "--JOURNALS DATA");
                write(writer, JOURNALS_INSERT_FORMAT, journals.subList(lastJournalsInd, journals.size()));
                write(writer, "--ARTICLES AND CONNECTING TABLES DATA");
                for (Article article : articles) {
                    write(writer, ARTICLES_INSERT_FORMAT,
                            article.title, article.publtype,
                            article.url, article.year + "");
                    write(writer, "--    AUTHORS");
                    for (Pair<String, String> author : article.authors) {
                        write(writer, ARTICLE_AUTHOR_INSERT_FORMAT,
                                article, author.getKey(), author.getValue());
                    }
                    write(writer, "--    KEYWORDS");
                    for (String keyword : article.keywords) {
                        write(writer, ARTICLE_KEYWORD_INSERT_FORMAT, article, keyword);
                    }
                    if (article instanceof JournalArticle) {
                        JournalArticle journalArticle = (JournalArticle) article;
                        write(writer, "--    JOURNAL");
                        write(writer, ARTICLE_JOURNAL_INSERT_FORMAT,
                                article, journalArticle.journal,
                                journalArticle.volume,
                                journalArticle.number);
                    }
                    if (article instanceof ConferenceArticle) {
                        write(writer, "--    CONFERENCE");
                        ConferenceArticle conferenceArticle = (ConferenceArticle) article;
                        write(writer, ARTICLE_CONFERENCE_INSERT_FORMAT,
                                article, conferenceArticle.conference);
                    }
                }
                System.out.println("DATA FLUSHED");
                articles.clear();
                lastConferencesInd = conferences.size();
                lastAuthorsInd = authors.size();
                lastKeyWordsInd = keyWords.size();
                lastJournalsInd = journals.size();
                time++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //TODO if we want to load to db directly
    private static class DBLoadArticleService extends InMemoryArticleService {
        final static String AUTHOR_SQL = "INSERT INTO authors(first_name, last_name) VALUES (?,?)";
        final static String JOURNAL_SQL = "INSERT INTO journals(name) VALUES (?)";
        final static String ARTICLE_SQL = "INSERT INTO " +
                "articles(title, publtype, url, year) " +
                "VALUES (?,?,?,?)";
        final static String ARTICLE_AUTHOR_SQL = "INSERT INTO article_author(article_id, author_id) " +
                "VALUES (" +
                "(SELECT id FROM articles WHERE title=? AND year=?)," +
                "(SELECT id FROM authors WHERE first_name=? AND last_name=?))";


        private PreparedStatement areaStm;
        private PreparedStatement journalStm;
        private PreparedStatement authorStm;
        private PreparedStatement articleStm;
        private PreparedStatement articleAreaStm;
        private PreparedStatement articleAuthorStm;
        private Connection connection;

        private String dbUrl;
        private String dbUserName;
        private String dbPassword;

        public DBLoadArticleService(String dbUrl, String dbUserName, String dbPassword) {
            super();
            this.dbUrl = dbUrl;
            this.dbUserName = dbUserName;
            this.dbPassword = dbPassword;
        }

        private void initConnection() throws SQLException {
            if (connection == null) {
                connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                journalStm = connection.prepareStatement(JOURNAL_SQL);
                authorStm = connection.prepareStatement(AUTHOR_SQL);
                articleStm = connection.prepareStatement(ARTICLE_SQL);
                articleAuthorStm = connection.prepareStatement(ARTICLE_AUTHOR_SQL);
            }
        }

//        private void initArticleStm(Article article) throws SQLException {
//            articleStm.setString(1, article.title);
//            articleStm.setString(2, article.publtype);
//            articleStm.setString(3, article.url);
//            articleStm.setString(4, article.journal);
//            articleStm.setInt(5, article.year);
//            articleStm.setString(6, article.volume);
//            articleStm.setString(7, article.number);
//        }

        @Override
        public void loadAll() {
            try {

            } finally {
                closeConnection();
            }
        }

        private void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
