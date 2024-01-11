import java.io.*;
import java.util.Scanner;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
public class DijkstrasPathFinder {
    private static final double[][] adjMatrix = new double[51][51];
    private static final String[] cities = new String[51];
    
    public static void main(String[] args) {
        fileToAdjacencyMatrix("distances.csv");

        dijkstra(cityToIndex("Portland ME"), cityToIndex("Seattle WA"));
        System.out.println();
        dijkstra(cityToIndex("Jacksonville FL"), cityToIndex("Seattle WA"));
        System.out.println();
        dijkstra(cityToIndex("Boston MA"), cityToIndex("Los Angeles CA"));
        System.out.println();
        dijkstra(cityToIndex("New York City NY"), cityToIndex("Los Angeles CA"));
        System.out.println();
        dijkstra(cityToIndex("Chicago IL"), cityToIndex("Honolulu HI"));
        System.out.println();
        dijkstra(cityToIndex("Nashville TN"), cityToIndex("Milwaukee WI"));
        System.out.println();
        dijkstra(cityToIndex("Milwaukee WI"), cityToIndex("Anchorage AK"));
        System.out.println();
        dijkstra(cityToIndex("Charlotte NC"), cityToIndex("Houston TX"));
        System.out.println();
        dijkstra(cityToIndex("Albuquerque NM"), cityToIndex("Portland OR"));
        System.out.println();
        dijkstra(cityToIndex("Philadelphia PA"), cityToIndex("Las Vegas NV"));
        System.out.println();
    }

    private static void fileToAdjacencyMatrix(String path) {
        File file = new File(path);
        try{
            Scanner inputStream = new Scanner(file);
            String towns = inputStream.nextLine();
            String[] splitData = towns.split(",");
            for(int i = 1; i <= cities.length; i++){
                cities[i-1] = splitData[i];

            }
            int row = 0;
            while (inputStream.hasNextLine()){
                String data = inputStream.nextLine();
                splitData = data.split(",");
                
                for(int j = 1; j <= adjMatrix[row].length;j++){
                    adjMatrix[row][j-1] = Double.parseDouble(splitData[j]);

                }
                row++;
            }
            inputStream.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static int cityToIndex(String city) {
        for(int i = 0; i < cities.length; i++){
            if(cities[i].compareToIgnoreCase(city) ==0){
                return i;
            }
        }
        return -1;
    }

    private static void dijkstra(int s, int t) {
        double[] dist = new double[51];
        for(int i = 0; i < dist.length;i++){
            dist[i] = Double.POSITIVE_INFINITY;
        }
        dist[s] = 0;
        ArrayList<Integer> open = new ArrayList<Integer>();
        open.add(s);
        int helper = 0;
        boolean[] closed = new boolean[51];
        int[] parent = new int[51];
        for(int i = 0; i < parent.length;i++){
            parent[i] = -1;
        }
        while(!(open.isEmpty())){
            int umin = open.get(0);
            for(int i = 0; i < open.size();i++){
                if(dist[open.get(i)] < dist[umin]){
                    umin = open.get(i);
                    
                    helper = i; 

                }
            }
            open.remove((Object)umin);
            closed[umin] = true;
            
            for( int v = 0; v < 51; v++){
                
                if(adjMatrix[umin][v] != -1 && closed[v] == false ){
                    double len = dist[umin] + adjMatrix[umin][v];
                    
                    if(len < dist[v]){
                        dist[v] = len;
                        parent[v] = umin;
                        
                        if(!open.contains(v)){
                            open.add(v)   ;

                        }
                    }
                }
            }
        }
        
        if(dist[t] == Double.POSITIVE_INFINITY){
            System.out.println("No paths from " + cities[s] + " to " + cities[t]);
            return;
        }
        ArrayList<Integer> paths = new ArrayList<Integer>();
        int curr = t;
        while(curr != -1){
            paths.add(curr);
            
            curr = parent[curr];
        }
        
        System.out.println("Path from " + cities[s] + " to " + cities[t] );
        for(int i = paths.size()-1; i > 0; i--){
            System.out.println(cities[i]);
            
        }
        System.out.println("Total Distance: " + dist[t] + " miles");
    }
}


