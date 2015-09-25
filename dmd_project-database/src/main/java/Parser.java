import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Филипп on 24.09.2015.
 */
public class Parser {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        Set<Article> articles = new HashSet<>();
        saxParser.parse(new FileInputStream(new File("C:\\Users\\Филипп\\Desktop\\dblp2\\dblp_aa.xml")),
                new saxParser("C:\\Users\\Филипп\\Desktop\\dblp2\\dblp_aa.xml", articles));
        String sql = null;
        FileWriter fw = new FileWriter(new File("sql.nah"));
        for (Article article : articles) {
            fw.write("INSERT INTO Articles (title, year, publtype, url) VALUES (" + article.getTitle() + ", " +
                article.getYear() + ", " + article.getPubtype() + ", " + article.getUrl() + ")\\n");
            String[ ] authors = article.getAuthor().split(" ");
            fw.write("INSERT INTO Authors (first_name, last_name) VALUES (" + authors[0] + ", " + authors[authors.length - 1] + ")\\n");
            fw.write("INSERT INTO Science_Areas (name) VALUES (" + article.getVenue() + ")\\n");
            fw.write("INSERT INTO Journals (name) VALUES (" + article.getJournal() + ")\\n");
            fw.write("INSERT INTO Article_Journal (article_id, journal_id, volume, number, pubtype) VALUES (SELECT id FROM Article WHERE title=" + article.getTitle() +
                " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Journals WHERE name=" + article.getJournal() + ", " +
                    article.getVolume() + ", " + article.getNumber() + ")\\n");
            fw.write("INSERT INTO Article_Author (article_id, author_id) VALUES (SELECT id FROM Article WHERE title=" + article.getTitle() +
                    " AND year=" + article.getYear() + " AND url=" + article.getUrl() + ", SELECT id FROM Authors WHERE first_name=" + authors[0] +
                        "AND last_name=" + authors[authors.length - 1] + ")\\n");
            fw.write("INSERT INTO Article_Area (article_id, area_id) VALUES(SELECT id FROM Article WHERE title=" + article.getTitle() +
                " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Science_Areas WHERE name=" +
                    article.getVenue() + ")\\n");
        }
        fw.close();

    }
}

