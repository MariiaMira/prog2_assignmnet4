import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Exercise4 {
	private Graph<Node> graph = new ListGraph<>();

    public void loadLocationGraph(String fileName){
        Map<String, Location> locations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line != null) {
                String [] locationsData = line.split(";");
                for (int i = 0; i < locationsData.length; i += 3) {
                    String cityName = locationsData[i];
                    double coordinateX = Double.parseDouble(locationsData[i+1]);
                    double coordinateY = Double.parseDouble(locationsData[i+2]);
                    Location location = new Location(cityName, coordinateX, coordinateY);
                    graph.add(location);
                    locations.put(cityName, location);
                }
            }

            while ((line = reader.readLine()) != null) {
                String [] edge = line.split(";");
                Location from = locations.get(edge[0]);
                Location to = locations.get(edge[1]);
                String medium = edge[2];
                int weight = Integer.parseInt(edge[3]);

                graph.connect(from, to, medium, weight);
            }
            //System.out.println(graph.toString()); // utskrift

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record item) {
        SortedMap<Integer, SortedSet<Record>> alsoLiked = new TreeMap<>(Comparator.reverseOrder());
        for(Edge<Node> edge1 : graph.getEdgesFrom(item)){
            Node person = edge1.getDestination();
            for(Edge<Node> edge2 : graph.getEdgesFrom(person)){
                Record record = (Record) edge2.getDestination();
                int popularity = getPopularity(record);
                SortedSet<Record> set = alsoLiked.get(popularity);
                if(set == null){
                    set = new TreeSet<>(Comparator.comparing(Record::toString));
                    //comparing tar fram vad som ska jämföras
                    alsoLiked.put(popularity, set);
                } // for if
                set.add(record);

            } //for edge2
        } // for edg1

        return alsoLiked;
    }

    public int getPopularity(Record item) {
       Collection<Edge<Node>> numberEdges = graph.getEdgesFrom(item);
       return numberEdges.size();
    }

    public SortedMap<Integer, Set<Record>> getTop5() {

       SortedMap<Integer, Set<Record>> topFive = new TreeMap<>(Collections.reverseOrder());

       for (Node node : graph.getNodes()){
            if (node instanceof Record){
                int popularity = getPopularity((Record) node);
                if (!topFive.containsKey(popularity)){

                    topFive.put(popularity, new HashSet<>());

                }
                topFive.get(popularity).add((Record) node);
            }
       }
        while (topFive.size() > 5) {
            topFive.pollLastEntry();
        }
       return topFive;
    }

    public void loadRecommendationGraph(String fileName) {
        Map<String, Person> personMap = new HashMap<>(); // hittar personer på Namn
        Set<Record> recordSet = new HashSet<>(); // hittar records på equals eller hashCode

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tempData = line.split(";");

                String personName = tempData[0].trim();
                String recordTitle = tempData[1].trim();
                String recordArtist = tempData[2].trim();

                Person person = personMap.get(personName); // hämtar person eller blir NULL
                if (person == null) { // om NULL
                    person = new Person(personName);
                    graph.add(person);
                    personMap.put(personName, person);
                }

                Record record = new Record(recordTitle, recordArtist);
                if (recordSet.add(record)) { // add() -> boolean true om den inte finns redan
                    graph.add(record);
                }
                graph.connect(person, record, "", 0); // final - lägg till en connection
            }
            //System.out.println(graph.toString()); // utskrift

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}