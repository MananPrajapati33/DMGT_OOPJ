// import java.util.*;

// public class Graph {
//     private Map<Integer, List<Integer>> adjacencyList;
//     private boolean isDirected;
    
//     public Graph(boolean isDirected) {
//         this.adjacencyList = new HashMap<>();
//         this.isDirected = isDirected;
//     }
    
//     public void addVertex(int vertex) {
//         adjacencyList.putIfAbsent(vertex, new ArrayList<>());
//     }
    
//     public void addEdge(int source, int destination) {
//         addVertex(source);
//         addVertex(destination);
        
//         adjacencyList.get(source).add(destination);
//         if (!isDirected) {
//             adjacencyList.get(destination).add(source);
//         }
//     }
    
//     public List<Integer> getNeighbors(int vertex) {
//         return adjacencyList.getOrDefault(vertex, new ArrayList<>());
//     }
    
//     public Set<Integer> getVertices() {
//         return adjacencyList.keySet();
//     }
    
//     public boolean isDirected() {
//         return isDirected;
//     }
    
//     public void removeEdge(int source, int destination) {
//         List<Integer> sourceNeighbors = adjacencyList.get(source);
//         if (sourceNeighbors != null) {
//             sourceNeighbors.remove(Integer.valueOf(destination));
//         }
        
//         if (!isDirected) {
//             List<Integer> destNeighbors = adjacencyList.get(destination);
//             if (destNeighbors != null) {
//                 destNeighbors.remove(Integer.valueOf(source));
//             }
//         }
//     }
    
//     public void removeVertex(int vertex) {
//         adjacencyList.remove(vertex);
//         for (List<Integer> neighbors : adjacencyList.values()) {
//             neighbors.remove(Integer.valueOf(vertex));
//         }
//     }
    
//     public int getVertexCount() {
//         return adjacencyList.size();
//     }
    
//     public int getEdgeCount() {
//         int count = 0;
//         for (List<Integer> neighbors : adjacencyList.values()) {
//             count += neighbors.size();
//         }
//         return isDirected ? count : count / 2;
//     }
    
//     public void clear() {
//         adjacencyList.clear();
//     }
    
//     public Graph copy() {
//         Graph newGraph = new Graph(isDirected);
//         for (int vertex : adjacencyList.keySet()) {
//             newGraph.addVertex(vertex);
//         }
//         for (int source : adjacencyList.keySet()) {
//             for (int destination : adjacencyList.get(source)) {
//                 newGraph.addEdge(source, destination);
//             }
//         }
//         return newGraph;
//     }
// }


import java.util.*;

public class Graph {
    // Using adjacency list because it's simple and efficient for most graphs
    private Map<Integer, List<Integer>> adjList;
    private boolean directed;

    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.directed = directed;
    }

    // Add vertex if it doesn't already exist
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    // Add edge between two vertices
    public void addEdge(int src, int dest) {
        addVertex(src);
        addVertex(dest);

        adjList.get(src).add(dest);

        // If graph is undirected, add reverse edge too
        if (!directed) {
            adjList.get(dest).add(src);
        }
    }

    public List<Integer> getNeighbors(int v) {
        return adjList.getOrDefault(v, new ArrayList<>());
    }

    public Set<Integer> getVertices() {
        return adjList.keySet();
    }

    public boolean isDirected() {
        return directed;
    }

    public void clear() {
        adjList.clear();
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}
