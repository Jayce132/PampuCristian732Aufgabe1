import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class Main {

    public static List<Spiel> readXmlgames(String filename) {
        List<Spiel> games = new ArrayList<>();
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList logNodes = doc.getElementsByTagName("log");

            for (int i = 0; i < logNodes.getLength(); i++) {
                Node node = logNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    int id = Integer.parseInt(getTagValue("Id", element));
                    String team1 = getTagValue("Team1", element);
                    String team2 = getTagValue("Team2", element);
                    String spielort = getTagValue("Spielort", element);
                    String datum = getTagValue("Datum", element);
                    int kapazität = Integer.parseInt(getTagValue("Kapazität", element));

                    games.add(new Spiel(id, team1, team2, spielort, kapazität, datum));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null && node.getFirstChild() != null) {
                return node.getFirstChild().getNodeValue();
            }
        }
        return null;
    }

    private static void displayGamesAboveCapacity(List<Spiel> games, int capacity) {
        List<Spiel> gamesAboveCapacity = games.stream().filter(x -> x.getKapazität() > capacity).toList();
        for (Spiel game : gamesAboveCapacity) {
            System.out.println(game.getTeam1() + " vs " + game.getTeam2() + " - Datum: " + game.getDatum() + " - Spielort: " + game.getSpielort());
        }
    }

    private static void displayGamesInCityAfterDate(List<Spiel> games, String date) {
        // Games in City after Date
        List<Spiel> gamesInMunchen =
                games.stream().filter(game1 -> game1.getSpielort().equals("München")).collect(Collectors.toList());

        gamesInMunchen.sort(Comparator.comparing(Spiel::getLocalDate));

        List<Spiel> gamesInMunchenAfterDate = gamesInMunchen.stream().filter(x -> x.getLocalDate().isAfter(LocalDate.parse(date))).toList();

        for (Spiel game : gamesInMunchenAfterDate) {
            System.out.println(game.getDatum() + ": " + game.getTeam1() + " vs " + game.getTeam2());
        }

    }

    private static void calculateAndWriteGamesPerCity(List<Spiel> games, String outputFileName) {
        // Use a Map to count events per house.
        Map<String, Integer> gameCountMap = new HashMap<>();
        for (Spiel e : games) {
            String city = e.getSpielort();
            gameCountMap.put(city, gameCountMap.getOrDefault(city, 0) + 1);
        }

        // Create a list of entries and sort them:
        // If two houses have the same event count, sort alphabetically.
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(gameCountMap.entrySet());
        sortedList.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if (cmp == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return cmp;
        });

        // Write results to output file with each line formatted as: House#Count
        try (FileWriter writer = new FileWriter(outputFileName)) {
            for (Map.Entry<String, Integer> entry : sortedList) {
                writer.write(entry.getKey() + "%" + entry.getValue() + "\n");
            }
            System.out.println("\nEvent counts per house written to " + outputFileName);
        } catch (IOException e) {
            System.err.println("Error writing to file " + outputFileName + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filename = "spielorte.xml";
        List<Spiel> games = readXmlgames(filename);
//        games.forEach(System.out::println);

        displayGamesAboveCapacity(games, 70000);
        displayGamesInCityAfterDate(games, "2024-06-30");
        calculateAndWriteGamesPerCity(games, "spielanzahl.txt");
    }
}
