package Data_Structures.unit_converter;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author William Hadden
 * 
 * Idea is to represent conversions as a graph
 * in this case an adjacentcy list 
 */
public class unit_converter {
    private int numVerticies; //verticies will be the units
    private HashMap<String, LinkedList<vertex>> lookup; //which also has all the verticies
     
    /** effectively a node in a linked list */
    private class vertex{
        private String name;
        private double weight;

        public vertex(String name){
            this.name = name;
        }
        public void set_weight(double weight){
            this.weight = weight;
        }

        public String get_name(){
            return this.name;
        }
        public double get_weight(){
            return this.weight;
        }

    }
    /**
     * Default constructor
     */
    public unit_converter(){
        this.lookup = new HashMap<>();
        this.numVerticies = 0;
    }

    public void add_information(String vertex1_name, String vertex2_name, double weight1, double weight2){
        vertex vertex1 = new vertex(vertex1_name);
        vertex vertex2 = new vertex(vertex2_name);
        
        vertex2.set_weight(weight2/weight1); //to get from v1 -> v2
        vertex1.set_weight(weight1/weight2); //to get from v2 -> v1

        //updating vertex1  need a way to deal with duplicates
        if (lookup.containsKey(vertex1.get_name())){ //if vertex1 already exists
            LinkedList<vertex> adj_list = lookup.get(vertex1.get_name()); //linked list for this key
            adj_list.add(vertex2); //add its destination vertex to end
            lookup.put(vertex1.get_name(), adj_list); //replace the value in vertex1
        } else { //vertex 1 doesn;t exist
            LinkedList<vertex> adj_list = new LinkedList<>();
            adj_list.add(vertex2);
            lookup.put(vertex1.get_name(), adj_list);
        }
        //updating vertex 2
        if (lookup.containsKey(vertex2.get_name())){
            LinkedList<vertex> adj_list = lookup.get(vertex2.get_name());
            adj_list.add(vertex1);
            lookup.put(vertex2.get_name(), adj_list);
        } else {
            LinkedList<vertex> adj_list = new LinkedList<>();
            adj_list.add(vertex1);
            lookup.put(vertex2.get_name(), adj_list);
        }
    }

    public void print_lookup(){
        Set<String> keys = lookup.keySet();

        for (String key : keys){ //for all the keys
            LinkedList<vertex> adj_list = lookup.get(key);
            System.out.printf("%s: ", key);
            for (vertex v : adj_list){
                System.out.printf(" %s, weight: %f ->", v.get_name(), v.get_weight());
            }
            System.out.println();
        }
    }

    /**
     * Convert a certain amount of unit1 to unit 2
     * @param unit1
     * @param unit2
     * @param amount
     * @return double of above
     */
    public double convert(String unit1, String unit2, double amount){
        if (!(lookup.containsKey(unit1) && lookup.containsKey(unit2))){
            System.out.println("Key not found");
            return 0.00;
        }
        if (unit1 == unit2){ return amount; }
        //run a dfs
        double conversion = DFS(unit1, unit2);

        return amount * conversion;
    }

    /**
     * Run dfs
     * @param vertex name of vertex/unit
     * @return
     */
    public double DFS(String v, String goal){
        LinkedList<vertex> adj_list = lookup.get(v);
        HashSet<String> visited = new HashSet<>();
        visited.add(v);
        double conversion = 0;
        for (vertex u : adj_list){
            if (!visited.contains(u.get_name())){
                visited.add(u.get_name());
                if (u.get_name() == goal){
                    return u.get_weight();
                }
                conversion = u.get_weight()  * DFS_visit(u, visited, goal);
            }
        }
        return conversion;
    }
    public double DFS_visit(vertex u, HashSet<String> visited, String goal){
        LinkedList<vertex> adj_list = lookup.get(u.get_name());
        double conversion = 0;
        for (vertex v : adj_list){
            if (!visited.contains(v.get_name())){
                visited.add(v.get_name());
                if (v.get_name() == goal){
                    return v.get_weight();
                } else {
                    conversion = v.get_weight() *
                    DFS_visit(v, visited, goal);
                }
            }
        }
        return conversion;
    }
}
