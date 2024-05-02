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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record item) {


        return null;
    }

    public int getPopularity(Record item) {
       return -1;
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
       return null;
    }

    public void loadRecommendationGraph(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tempData = line.split(";");
                Person p = new Person(tempData[0]);
                Record r = new Record(tempData[1], tempData[2]);

                graph.add(p);
                graph.add(r);
                graph.connect(p,r, "", 0);
            }

            System.out.println(graph); // KOMMENTAR ATT TA BOORT

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
