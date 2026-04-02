// import java.util.*;

// public class DFSTraversal {
//     private Graph graph;
//     private List<Integer> traversalOrder;
//     private Map<Integer, Integer> discoveryTime;
//     private Map<Integer, Integer> finishTime;
//     private Map<Integer, Integer> parents;
//     private List<String> steps;
//     private int time;
    
//     public DFSTraversal(Graph graph) {
//         this.graph = graph;
//         this.traversalOrder = new ArrayList<>();
//         this.discoveryTime = new HashMap<>();
//         this.finishTime = new HashMap<>();
//         this.parents = new HashMap<>();
//         this.steps = new ArrayList<>();
//         this.time = 0;
//     }
    
//     public void traverse(int startVertex) {
//         traversalOrder.clear();
//         discoveryTime.clear();
//         finishTime.clear();
//         parents.clear();
//         steps.clear();
//         time = 0;
        
//         Set<Integer> visited = new HashSet<>();
        
//         steps.add("Starting DFS from vertex " + startVertex);
        
//         dfsRecursive(startVertex, visited);
        
//         steps.add("\nDFS Traversal Complete!");
//         steps.add("Traversal Order: " + traversalOrder);
//         steps.add("Discovery Times: " + discoveryTime);
//         steps.add("Finish Times: " + finishTime);
//         steps.add("Parents: " + parents);
//     }
    
//     public void traverseAll() {
//         traversalOrder.clear();
//         discoveryTime.clear();
//         finishTime.clear();
//         parents.clear();
//         steps.clear();
//         time = 0;
        
//         Set<Integer> visited = new HashSet<>();
        
//         steps.add("Starting DFS for all vertices");
        
//         for (int vertex : graph.getVertices()) {
//             if (!visited.contains(vertex)) {
//                 steps.add("\nStarting new DFS component from vertex " + vertex);
//                 dfsRecursive(vertex, visited);
//             }
//         }
        
//         steps.add("\nDFS Traversal Complete!");
//         steps.add("Traversal Order: " + traversalOrder);
//         steps.add("Discovery Times: " + discoveryTime);
//         steps.add("Finish Times: " + finishTime);
//         steps.add("Parents: " + parents);
//     }
    
//     private void dfsRecursive(int vertex, Set<Integer> visited) {
//         visited.add(vertex);
//         time++;
//         discoveryTime.put(vertex, time);
//         parents.put(vertex, -1);
//         traversalOrder.add(vertex);
        
//         steps.add("\nVisiting vertex " + vertex);
//         steps.add("Discovery time: " + time);
        
//         for (int neighbor : graph.getNeighbors(vertex)) {
//             if (!visited.contains(neighbor)) {
//                 parents.put(neighbor, vertex);
//                 steps.add("  Moving to unvisited neighbor " + neighbor);
//                 steps.add("  Parent of " + neighbor + " is " + vertex);
//                 dfsRecursive(neighbor, visited);
//             } else {
//                 if (discoveryTime.get(neighbor) > discoveryTime.get(vertex) && 
//                     !finishTime.containsKey(neighbor)) {
//                     steps.add("  Neighbor " + neighbor + " is a back edge (currently in recursion stack)");
//                 } else if (finishTime.containsKey(neighbor)) {
//                     steps.add("  Neighbor " + neighbor + " is a cross/forward edge");
//                 }
//             }
//         }
        
//         time++;
//         finishTime.put(vertex, time);
//         steps.add("Finished vertex " + vertex + " at time " + time);
//     }
    
//     public List<Integer> getTraversalOrder() {
//         return new ArrayList<>(traversalOrder);
//     }
    
//     public Map<Integer, Integer> getDiscoveryTime() {
//         return new HashMap<>(discoveryTime);
//     }
    
//     public Map<Integer, Integer> getFinishTime() {
//         return new HashMap<>(finishTime);
//     }
    
//     public Map<Integer, Integer> getParents() {
//         return new HashMap<>(parents);
//     }
    
//     public List<String> getSteps() {
//         return new ArrayList<>(steps);
//     }
    
//     public boolean hasCycle() {
//         for (int vertex : graph.getVertices()) {
//             for (int neighbor : graph.getNeighbors(vertex)) {
//                 if (discoveryTime.containsKey(neighbor) && 
//                     discoveryTime.get(vertex) < discoveryTime.get(neighbor) &&
//                     finishTime.containsKey(vertex) && 
//                     finishTime.get(vertex) > finishTime.get(neighbor)) {
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }
    
//     public void printTraversalDetails() {
//         System.out.println("=== DFS Traversal Details ===");
//         for (String step : steps) {
//             System.out.println(step);
//         }
//     }
// }


import java.util.*;

public class DFSTraversal {
    @SuppressWarnings("FieldMayBeFinal")
    private Graph graph;
    @SuppressWarnings("FieldMayBeFinal")
    private List<Integer> order;
    @SuppressWarnings("FieldMayBeFinal")
    private List<String> steps;
    private Set<Integer> visited;

    public DFSTraversal(Graph graph) {
        this.graph = graph;
        order = new ArrayList<>();
        steps = new ArrayList<>();
        visited = new HashSet<>();
    }

    public DFSTraversal(Graph graph, List<Integer> order, List<String> steps, Set<Integer> visited) {
        this.graph = graph;
        this.order = order;
        this.steps = steps;
        this.visited = visited;
    }

    public void traverse(int start) {
        order.clear();
        steps.clear();
        visited.clear();

        steps.add("Starting DFS from " + start);

        dfs(start);

        steps.add("\nDone DFS");
        steps.add("Order: " + order);
    }

    // Recursive DFS (simple and readable)
    private void dfs(int node) {
        visited.add(node);
        order.add(node);

        steps.add("Visiting " + node);

        for (int nbr : graph.getNeighbors(node)) {
            if (!visited.contains(nbr)) {
                steps.add(" -> Going to " + nbr);
                dfs(nbr);
            }
        }
    }

    public List<Integer> getTraversalOrder() {
        return order;
    }

    public List<String> getSteps() {
        return steps;
    }

    public Set<Integer> getVisited() {
        return visited;
    }
}
