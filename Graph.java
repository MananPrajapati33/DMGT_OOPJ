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
