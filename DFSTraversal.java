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
