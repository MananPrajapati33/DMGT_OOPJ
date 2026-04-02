// import java.util.*;

// public class BFSTraversal {
//     private Graph graph;
//     private List<Integer> traversalOrder;
//     private Map<Integer, Integer> distances;
//     private Map<Integer, Integer> parents;
//     private List<String> steps;
    
//     public BFSTraversal(Graph graph) {
//         this.graph = graph;
//         this.traversalOrder = new ArrayList<>();
//         this.distances = new HashMap<>();
//         this.parents = new HashMap<>();
//         this.steps = new ArrayList<>();
//     }
    
//     public void traverse(int startVertex) {
//         traversalOrder.clear();
//         distances.clear();
//         parents.clear();
//         steps.clear();
        
//         Queue<Integer> queue = new LinkedList<>();
//         Set<Integer> visited = new HashSet<>();
        
//         queue.add(startVertex);
//         visited.add(startVertex);
//         distances.put(startVertex, 0);
//         parents.put(startVertex, -1);
        
//         steps.add("Starting BFS from vertex " + startVertex);
//         steps.add("Queue: " + queue);
//         steps.add("Visited: " + visited);
        
//         while (!queue.isEmpty()) {
//             int current = queue.poll();
//             traversalOrder.add(current);
            
//             steps.add("\nProcessing vertex " + current);
//             steps.add("Distance from start: " + distances.get(current));
            
//             for (int neighbor : graph.getNeighbors(current)) {
//                 if (!visited.contains(neighbor)) {
//                     visited.add(neighbor);
//                     queue.add(neighbor);
//                     distances.put(neighbor, distances.get(current) + 1);
//                     parents.put(neighbor, current);
                    
//                     steps.add("  Found unvisited neighbor " + neighbor);
//                     steps.add("  Distance: " + distances.get(neighbor));
//                     steps.add("  Parent: " + current);
//                 } else {
//                     steps.add("  Neighbor " + neighbor + " already visited");
//                 }
//             }
            
//             steps.add("Queue after processing " + current + ": " + queue);
//         }
        
//         steps.add("\nBFS Traversal Complete!");
//         steps.add("Traversal Order: " + traversalOrder);
//         steps.add("Distances: " + distances);
//         steps.add("Parents: " + parents);
//     }
    
//     public List<Integer> getTraversalOrder() {
//         return new ArrayList<>(traversalOrder);
//     }
    
//     public Map<Integer, Integer> getDistances() {
//         return new HashMap<>(distances);
//     }
    
//     public Map<Integer, Integer> getParents() {
//         return new HashMap<>(parents);
//     }
    
//     public List<String> getSteps() {
//         return new ArrayList<>(steps);
//     }
    
//     public List<Integer> getShortestPath(int start, int end) {
//         if (!distances.containsKey(end)) {
//             return new ArrayList<>();
//         }
        
//         List<Integer> path = new ArrayList<>();
//         int current = end;
        
//         while (current != -1) {
//             path.add(0, current);
//             current = parents.get(current);
//         }
        
//         return path;
//     }
    
//     public void printTraversalDetails() {
//         System.out.println("=== BFS Traversal Details ===");
//         for (String step : steps) {
//             System.out.println(step);
//         }
//     }
// }

import java.util.*;

public class BFSTraversal {
    @SuppressWarnings("FieldMayBeFinal")
    private Graph graph;
    @SuppressWarnings("FieldMayBeFinal")
    private List<Integer> order;
    @SuppressWarnings("FieldMayBeFinal")
    private Map<Integer, Integer> distance;
    private Map<Integer, Integer> parent;
    @SuppressWarnings("FieldMayBeFinal")
    private List<String> steps;

    public BFSTraversal(Graph graph) {
        this.graph = graph;
        order = new ArrayList<>();
        distance = new HashMap<>();
        parent = new HashMap<>();
        steps = new ArrayList<>();
    }

    public void traverse(int start) {
        order.clear();
        distance.clear();
        parent.clear();
        steps.clear();

        // Queue is important for BFS (level by level)
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        q.add(start);
        visited.add(start);
        distance.put(start, 0);
        parent.put(start, -1);

        steps.add("Starting BFS from " + start);

        while (!q.isEmpty()) {
            int current = q.poll();
            order.add(current);

            steps.add("\nVisiting " + current);

            for (int nbr : graph.getNeighbors(current)) {
                if (!visited.contains(nbr)) {
                    visited.add(nbr);
                    q.add(nbr);

                    distance.put(nbr, distance.get(current) + 1);
                    parent.put(nbr, current);

                    steps.add(" -> Found " + nbr + " (distance " + distance.get(nbr) + ")");
                }
            }
        }

        steps.add("\nDone BFS");
        steps.add("Order: " + order);
    }

    public List<Integer> getTraversalOrder() {
        return order;
    }

    public List<String> getSteps() {
        return steps;
    }
}
