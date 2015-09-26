import models.Article;
import models.Author;
import models.Journal;
import models.ScienceArea;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Филипп on 24.09.2015.
 */
public class Parser {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        Set<Article> articles = new HashSet<>();
        Set <Author> authors = new HashSet<>();
        Set<Journal> journals = new HashSet<>();
        Set<ScienceArea> scienceAreas = new HashSet<>();
        String[] files = new String[45];
        String alphabetic = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 26; i++) {
            files[i] = "dblp_a" + alphabetic.charAt(i) + ".xml";
        }
        int j = 0;
        for (int i = 26; i < 45; i++) {
            files[i] = "dblp_a" + alphabetic.charAt(j) + ".xml";
            j++;
        }
        for (String file : files) {
            saxParser.parse(new FileInputStream(new File("C:\\Users\\Филипп\\Desktop\\dblp2\\dblp_aa.xml")),
                    new SaxParser("C:\\Users\\Филипп\\Desktop\\dblp2\\dblp_aa.xml", articles, authors, journals, scienceAreas));
            sendToBD(articles, authors, journals, scienceAreas);
            articles = new HashSet<>();
            authors = new HashSet<>();
            journals = new HashSet<>();
            scienceAreas = new HashSet<>();
        }
        /*String sql = null;
        FileWriter fw = new FileWriter(new File("sql.nah"));
        for (models.Article article : articles) {
            fw.write("INSERT INTO Articles (title, year, publtype, url) VALUES (" + article.getTitle() + ", " +
                    article.getYear() + ", " + article.getPubtype() + ", " + article.getUrl() + ")\n");
            String[ ] authorsS = article.getAuthor().split(" ");
            fw.write("INSERT INTO Authors (first_name, last_name) VALUES (" + authorsS[0] + ", " + authorsS[authorsS.length - 1] + ")\n");
            fw.write("INSERT INTO Science_Areas (name) VALUES (" + article.getVenue() + ")\n");
            fw.write("INSERT INTO Journals (name) VALUES (" + article.getJournal() + ")\n");
            fw.write("INSERT INTO Article_Journal (article_id, journal_id, volume, number, pubtype) VALUES (SELECT id FROM models.Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Article_Journal WHERE name=" + article.getJournal() + ", " +
                    article.getVolume() + ", " + article.getNumber() + ", " + article.getPubtype() + ")\n");
            fw.write("INSERT INTO Article_Author (article_id, author_id) VALUES (SELECT id FROM models.Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() + ", SELECT id FROM Authors WHERE first_name=" + authorsS[0] +
                    "AND last_name=" + authorsS[authorsS.length - 1] + ")\n");
            fw.write("INSERT INTO Article_Area (article_id, area_id) VALUES(SELECT id FROM models.Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Science_Areas WHERE name=" + article.getVenue() + ")\n");
        }
        fw.close();*/




    }

    private static void sendToBD(Set <Article> articles, Set <Author> authors, Set <Journal> journals, Set <ScienceArea> scienceAreas) throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Author author : authors) {
            statement.execute("INSERT INTO Authors (first_name, last_name) VALUES ("+ author.getFirstname() + "," + author.getLastname() + ")");
        }
        for (Journal journal : journals) {
            statement.execute("INSERT INTO Journals (name) VALUES (" + journal.getName() + ")");
        }
        for (ScienceArea scienceArea : scienceAreas) {
            statement.execute("INSERT INTO Science_Areas (name) VALUES (" + scienceArea.getName() + ")");
        }
        for (Article article : articles) {
            String[ ] authorsS = article.getAuthor().split(" ");
            statement.execute("INSERT INTO Articles (title, year, publtype, url) VALUES (" + article.getTitle() + ", " +
                    article.getYear() + ", " + article.getPubtype() + ", " + article.getUrl() + ")");
            statement.execute("INSERT INTO Article_Author (article_id, author_id) VALUES (SELECT id FROM Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() + ", SELECT id FROM Authors WHERE first_name=" + authorsS[0] +
                    "AND last_name=" + authorsS[authorsS.length - 1] + ")");
            statement.execute("INSERT INTO Article_Area (article_id, area_id) VALUES(SELECT id FROM Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Science_Areas WHERE name=" + article.getVenue() + ")");
        }
    }
}

