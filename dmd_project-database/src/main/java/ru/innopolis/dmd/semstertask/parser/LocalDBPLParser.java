package ru.innopolis.dmd.semstertask.parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by plov on 19.09.15.
 */
public class LocalDBPLParser {
    Set<String> names, journals, publishers, series, schools;
    private String dblp;

    public LocalDBPLParser() {
        dblp = "/home/timur/Documents/innopolis/dmd/semestr task/dblp.xml";
        names = getEntityNames();
        journals = new HashSet<String>();
        publishers = new HashSet<String>();
        series = new HashSet<String>();
        schools = new HashSet<String>();
    }

    public void parseLocalDBLP() throws XMLStreamException, FileNotFoundException {
        if (!new File(dblp).exists()) return;
        long start = System.currentTimeMillis();
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(dblp));
        long entityNumber = 0;
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String str = reader.getLocalName();
                if (names.contains(str)) {
                    System.out.println(getEntity(reader, str));
                    entityNumber++;
                }
            }

        }
        long finish = System.currentTimeMillis();
        System.out.println(((finish - start) / 1000) + " seconds. " + entityNumber + " entities.");
    }

    public Set<String> getEntityNames() {
        Set<String> set = new HashSet<>();
        set.add("article");
        set.add("inproceedings");
        set.add("proceedings");
        set.add("book");
        set.add("incollection");
        set.add("phdthesis");
        set.add("mastersthesis");
        set.add("www");
        return set;
    }

    public Map<String, String> getEntity(XMLStreamReader reader, String entityName) throws XMLStreamException {
        Map<String, String> tuple = new HashMap<>();
        tuple.put("type", entityName);
        String currentTagName = null, tagContent;
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            tuple.put(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
        }
        FINDING:
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentTagName = reader.getLocalName();
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    if (currentTagName != null && tagContent != null) tuple.put(currentTagName, tagContent);
                    currentTagName = null;
                    tagContent = null;
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (entityName.equals(reader.getLocalName())) break FINDING;
                    break;

                case XMLStreamConstants.START_DOCUMENT:

                    break;
            }

        }
//        for (Map.Entry<String, String> entry:  tuple.entrySet()) {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
//        if (tuple.containsKey("journal")) journals.add(tuple.get("journal"));
//        if (tuple.containsKey("publisher")) publishers.add(tuple.get("journal"));
//        if (tuple.containsKey("series")) series.add(tuple.get("journal"));
//        if (tuple.containsKey("school")) schools.add(tuple.get("journal"));
        return tuple;
    }

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        new LocalDBPLParser().parseLocalDBLP();
    }
}
