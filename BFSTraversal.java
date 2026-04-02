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
