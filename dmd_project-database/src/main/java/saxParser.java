import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Филипп on 25.09.2015.
 */
class saxParser extends HandlerBase {
        private String nameElement;
        private String url;
        private boolean wasError = false;
        private Article article = null;
        Set<Article> articles;
        String beginSearchURL = "http://dblp.uni-trier.de/search/publ/api?q=";
        String endSearchURL = "&h=1000&format=xml";
        FileWriter fw;

        public saxParser(String url_str, Set<Article> articles) throws IOException {
            url = url_str;
            fw  = new FileWriter(new File("sql.nah1"));
            this.articles = articles;
        }

        // =======================================================
        // Обработчики событий. Методы интерфейса DocumentHandler
        // ========================
        // Начало документа
        public void startDocument() {
        }

        // Конец документа
        public void endDocument() {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Встретился открывающий тэг элемента //
        public void startElement(String name, AttributeList attrs) {
            if (name.equals("article")) {
                article = new Article();
                if (attrs.getValue("pubtype") != null) {
                    article.setPubtype(attrs.getValue("pubtype"));
                }
            }
            nameElement = name;
        }

        // Встретился закрывающий тэг элемента
        public void endElement(String name) {
            if (name.equals("article")) {
                if (!wasError) {
                    Document doc = null;
                    Elements elements = null;
                    try {
                        doc = Jsoup.connect("http://dblp.uni-trier.de/search/publ/api?q=" + article.getTitle() + "&h=1000&format=xml").get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        elements = doc.getElementsByTag("venue");
                        Element element = elements.get(0);
                        article.setVenue(element.text());
                    }catch (Exception e){
                        wasError = true;
                    }
                    try {
                        fw.write("INSERT INTO Articles (title, year, publtype, url) VALUES (" + article.getTitle() + ", " +
                                article.getYear() + ", " + article.getPubtype() + ", " + article.getUrl() + ")\n");
                        String[ ] authors = article.getAuthor().split(" ");
                        fw.write("INSERT INTO Authors (first_name, last_name) VALUES (" + authors[0] + ", " + authors[authors.length - 1] + ")\n");
                        fw.write("INSERT INTO Science_Areas (name) VALUES (" + article.getVenue() + ")\n");
                        fw.write("INSERT INTO Journals (name) VALUES (" + article.getJournal() + ")\n");
                        fw.write("INSERT INTO Article_Journal (article_id, journal_id, volume, number, pubtype) VALUES (SELECT id FROM Article WHERE title=" + article.getTitle() +
                                " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Article_Journal WHERE name=" + article.getJournal() + ", " +
                                article.getVolume() + ", " + article.getNumber() + ", " + article.getPubtype() + ")\n");
                        fw.write("INSERT INTO Article_Author (article_id, author_id) VALUES (SELECT id FROM Article WHERE title=" + article.getTitle() +
                                " AND year=" + article.getYear() + " AND url=" + article.getUrl() + ", SELECT id FROM Authors WHERE first_name=" + authors[0] +
                                "AND last_name=" + authors[authors.length - 1] + ")\n");
                        fw.write("INSERT INTO Article_Area (article_id, area_id) VALUES(SELECT id FROM Article WHERE title=" + article.getTitle() +
                                " AND year=" + article.getYear() + " AND url=" + article.getUrl() +", SELECT id FROM Science_Areas WHERE name=" + article.getVenue() + ")\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(!wasError) {
                        articles.add(article);
                        System.out.println(article.getVenue());
                    }
                    wasError = false;
                } else {
                    article = null;
                    wasError = false;
                }
            }
            nameElement = null;
        }

        // Текстовые символы
        public void characters(char ch[], int start, int length) {
            String field = new String(ch, start, length);
            try {
                switch (nameElement) {
                    case "title":
                        article.setTitle(field);
                        break;
                    case "year":
                        article.setYear(Integer.parseInt(field));
                        break;
                    case "volume":
                        article.setVolume(Integer.parseInt(field));
                        break;
                    case "journal":
                        article.setJournal(field);
                        break;
                    case "number":
                        article.setNumber(Integer.parseInt(field));
                        break;
                    case "author":
                        article.setAuthor(field);
                        break;
                    case "url":
                        article.setUrl(field);
                }
            } catch (Exception e) {
                wasError = true;
            }
        }

        // Не обрабатываемые символы(например, содержимое секции CDATA)
        public void ignorableWhitespace(char ch[], int start, int length) {

        }

        // Инструкции XML-процессору
        public void processingInstruction(String target, String data) {
        }

        // ===================================================
        // Методы интерфейса ErrorHandler
        // ===============================
        // Последнее предупреждение
        public void warning(SAXParseException ex) {
            System.err.println("Warning at " + ex.getLineNumber() + " . " + ex.getColumnNumber() + "  -  "
                    + ex.getMessage());
        }

        // Произошла ошибка
        public void error(SAXParseException ex) {
            System.err.println("Error at {" + ex.getLineNumber() + "." + ex.getColumnNumber() + "  -  " + ex.getMessage());
        }

        // Такие ошибки исправить уже нельзя
        public void fatalError(SAXParseException ex) throws SAXException {
            System.err.println("Fatal error at {" + ex.getLineNumber() + " . " + ex.getColumnNumber() + "  -  "
                    + ex.getMessage());
            throw ex;
        }

        // =======================================================
        // Вывести информацию о документе
        // ===============================
        public void printInfo() {
            System.out.println("Документ " + url + " был успешно обработан");
        }
}