import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

public class GraphSimulator extends JFrame {
    private Graph graph;
    private BFSTraversal bfsTraversal;
    private DFSTraversal dfsTraversal;
    
    private GraphPanel graphPanel;
    private JTextArea outputArea;
    private JButton addVertexBtn, addEdgeBtn, bfsBtn, dfsBtn, clearBtn;
    private JTextField vertexField, sourceField, destField;
    private JCheckBox directedCheckBox;
    
    private Map<Integer, Point> vertexPositions;
    private List<Integer> currentTraversal;
    private int traversalIndex;
    private Timer animationTimer;
    private boolean isAnimating;
    
    public GraphSimulator() {
        graph = new Graph(false);
        bfsTraversal = new BFSTraversal(graph);
        dfsTraversal = new DFSTraversal(graph);
        vertexPositions = new HashMap<>();
        currentTraversal = new ArrayList<>();
        traversalIndex = 0;
        isAnimating = false;
        
        initializeUI();
        createSampleGraph();
    }
    
    private void initializeUI() {
        setTitle("Graph Traversal Simulator - BFS & DFS Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        
        // Control Panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Graph controls
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("Vertex:"), gbc);
        gbc.gridx = 1;
        vertexField = new JTextField(5);
        controlPanel.add(vertexField, gbc);
        gbc.gridx = 2;
        addVertexBtn = new JButton("Add Vertex");
        addVertexBtn.addActionListener(e -> addVertex());
        controlPanel.add(addVertexBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(new JLabel("Edge (From-To):"), gbc);
        gbc.gridx = 1;
        sourceField = new JTextField(5);
        controlPanel.add(sourceField, gbc);
        gbc.gridx = 2;
        destField = new JTextField(5);
        controlPanel.add(destField, gbc);
        gbc.gridx = 3;
        addEdgeBtn = new JButton("Add Edge");
        addEdgeBtn.addActionListener(e -> addEdge());
        controlPanel.add(addEdgeBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        directedCheckBox = new JCheckBox("Directed Graph");
        directedCheckBox.addActionListener(e -> toggleDirected());
        controlPanel.add(directedCheckBox, gbc);
        
        // Traversal controls
        gbc.gridx = 0; gbc.gridy = 3;
        bfsBtn = new JButton("Run BFS");
        bfsBtn.addActionListener(e -> runBFS());
        controlPanel.add(bfsBtn, gbc);
        gbc.gridx = 1;
        dfsBtn = new JButton("Run DFS");
        dfsBtn.addActionListener(e -> runDFS());
        controlPanel.add(dfsBtn, gbc);
        gbc.gridx = 2;
        clearBtn = new JButton("Clear Graph");
        clearBtn.addActionListener(e -> clearGraph());
        controlPanel.add(clearBtn, gbc);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // Main content area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Graph panel
        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(600, 600));
        splitPane.setLeftComponent(graphPanel);
        
        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(400, 600));
        splitPane.setRightComponent(scrollPane);
        
        splitPane.setDividerLocation(700);
        add(splitPane, BorderLayout.CENTER);
        
        // Animation timer with fixed 1 second delay
        animationTimer = new Timer(1000, e -> animateTraversal());
    }
    
    private void createSampleGraph() {
        // Create a sample graph for demonstration
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 7);
        graph.addEdge(5, 8);
        graph.addEdge(6, 8);
        
        // Arrange vertices in a nice layout
        int width = 600;
        vertexPositions.put(0, new Point(width/2, 100));
        vertexPositions.put(1, new Point(width/3, 200));
        vertexPositions.put(2, new Point(2*width/3, 200));
        vertexPositions.put(3, new Point(width/4, 300));
        vertexPositions.put(4, new Point(width/2, 300));
        vertexPositions.put(5, new Point(3*width/4, 300));
        vertexPositions.put(6, new Point(width/6, 400));
        vertexPositions.put(7, new Point(width/3, 400));
        vertexPositions.put(8, new Point(2*width/3, 400));
        
        graphPanel.repaint();
        outputArea.setText("Sample graph created with 9 vertices.\n");
        outputArea.append("Click 'Run BFS' or 'Run DFS' to start traversal visualization.\n");
    }
    
    private void addVertex() {
        try {
            int vertex = Integer.parseInt(vertexField.getText());
            graph.addVertex(vertex);
            if (!vertexPositions.containsKey(vertex)) {
                Random rand = new Random();
                int x = 50 + rand.nextInt(500);
                int y = 50 + rand.nextInt(500);
                vertexPositions.put(vertex, new Point(x, y));
            }
            graphPanel.repaint();
            outputArea.append("Added vertex: " + vertex + "\n");
            vertexField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for vertex.");
        }
    }
    
    private void addEdge() {
        try {
            int source = Integer.parseInt(sourceField.getText());
            int dest = Integer.parseInt(destField.getText());
            graph.addEdge(source, dest);
            graphPanel.repaint();
            outputArea.append("Added edge: " + source + " -> " + dest + "\n");
            sourceField.setText("");
            destField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for source and destination.");
        }
    }
    
    private void toggleDirected() {
        boolean isDirected = directedCheckBox.isSelected();
        Graph newGraph = new Graph(isDirected);
        
        // Copy all vertices and edges to new graph
        for (int vertex : graph.getVertices()) {
            newGraph.addVertex(vertex);
        }
        
        for (int source : graph.getVertices()) {
            for (int dest : graph.getNeighbors(source)) {
                newGraph.addEdge(source, dest);
            }
        }
        
        graph = newGraph;
        bfsTraversal = new BFSTraversal(graph);
        dfsTraversal = new DFSTraversal(graph);
        graphPanel.repaint();
        outputArea.append("Graph changed to: " + (isDirected ? "Directed" : "Undirected") + "\n");
    }
    
    private void runBFS() {
        if (graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add vertices to the graph first.");
            return;
        }
        
        Integer startVertex = getStartVertex();
        if (startVertex == null) return;
        
        bfsTraversal = new BFSTraversal(graph);
        bfsTraversal.traverse(startVertex);
        currentTraversal = bfsTraversal.getTraversalOrder();
        startAnimation("BFS", bfsTraversal.getSteps());
    }
    
    private void runDFS() {
        if (graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add vertices to the graph first.");
            return;
        }
        
        Integer startVertex = getStartVertex();
        if (startVertex == null) return;
        
        dfsTraversal = new DFSTraversal(graph);
        dfsTraversal.traverse(startVertex);
        currentTraversal = dfsTraversal.getTraversalOrder();
        startAnimation("DFS", dfsTraversal.getSteps());
    }
    
    private Integer getStartVertex() {
        String input = JOptionPane.showInputDialog(this, "Enter starting vertex:");
        if (input == null) return null;
        
        try {
            int vertex = Integer.parseInt(input);
            if (!graph.getVertices().contains(vertex)) {
                JOptionPane.showMessageDialog(this, "Vertex " + vertex + " does not exist in the graph.");
                return null;
            }
            return vertex;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
            return null;
        }
    }
    
    private void startAnimation(String algorithm, List<String> steps) {
        if (isAnimating) {
            animationTimer.stop();
            isAnimating = false;
        }
        
        traversalIndex = 0;
        outputArea.setText("=== " + algorithm + " Traversal ===\n\n");
        
        for (String step : steps) {
            outputArea.append(step + "\n");
        }
        
        isAnimating = true;
        animationTimer.start();
    }
    
    private void animateTraversal() {
        if (traversalIndex < currentTraversal.size()) {
            graphPanel.setHighlightedVertex(currentTraversal.get(traversalIndex));
            graphPanel.repaint();
            traversalIndex++;
        } else {
            animationTimer.stop();
            isAnimating = false;
            graphPanel.setHighlightedVertex(-1);
            graphPanel.repaint();
            outputArea.append("\n=== Animation Complete ===\n");
        }
    }
    
    private void clearGraph() {
        graph.clear();
        vertexPositions.clear();
        currentTraversal.clear();
        traversalIndex = 0;
        if (isAnimating) {
            animationTimer.stop();
            isAnimating = false;
        }
        graphPanel.setHighlightedVertex(-1);
        graphPanel.repaint();
        outputArea.setText("Graph cleared.\n");
    }
    
    private class GraphPanel extends JPanel {
        private int highlightedVertex = -1;
        
        public void setHighlightedVertex(int vertex) {
            this.highlightedVertex = vertex;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw edges
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            
            for (int source : graph.getVertices()) {
                Point sourcePos = vertexPositions.get(source);
                if (sourcePos == null) continue;
                
                for (int dest : graph.getNeighbors(source)) {
                    Point destPos = vertexPositions.get(dest);
                    if (destPos == null) continue;
                    
                    // Draw edge only once for undirected graphs
                    if (!graph.isDirected() || source < dest) {
                        g2d.drawLine(sourcePos.x, sourcePos.y, destPos.x, destPos.y);
                        
                        // Draw arrow for directed graphs
                        if (graph.isDirected()) {
                            drawArrow(g2d, sourcePos, destPos);
                        }
                    }
                }
            }
            
            // Draw vertices
            for (int vertex : graph.getVertices()) {
                Point pos = vertexPositions.get(vertex);
                if (pos == null) continue;
                
                // Highlight current vertex in traversal
                if (vertex == highlightedVertex) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(pos.x - 20, pos.y - 20, 40, 40);
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawOval(pos.x - 20, pos.y - 20, 40, 40);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillOval(pos.x - 15, pos.y - 15, 30, 30);
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawOval(pos.x - 15, pos.y - 15, 30, 30);
                }
                
                // Draw vertex label
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = g2d.getFontMetrics();
                String label = String.valueOf(vertex);
                int x = pos.x - fm.stringWidth(label) / 2;
                int y = pos.y + fm.getHeight() / 4;
                g2d.drawString(label, x, y);
            }
        }
        
        private void drawArrow(Graphics2D g2d, Point from, Point to) {
            int dx = to.x - from.x;
            int dy = to.y - from.y;
            double angle = Math.atan2(dy, dx);
            
            // Calculate arrow position (near the destination vertex)
            int arrowX = to.x - (int)(25 * Math.cos(angle));
            int arrowY = to.y - (int)(25 * Math.sin(angle));
            
            // Draw arrow lines
            int arrowLength = 10;
            int arrowAngle = 30;
            
            int x1 = arrowX - (int)(arrowLength * Math.cos(angle - Math.toRadians(arrowAngle)));
            int y1 = arrowY - (int)(arrowLength * Math.sin(angle - Math.toRadians(arrowAngle)));
            
            int x2 = arrowX - (int)(arrowLength * Math.cos(angle + Math.toRadians(arrowAngle)));
            int y2 = arrowY - (int)(arrowLength * Math.sin(angle + Math.toRadians(arrowAngle)));
            
            g2d.drawLine(arrowX, arrowY, x1, y1);
            g2d.drawLine(arrowX, arrowY, x2, y2);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphSimulator simulator = new GraphSimulator();
            simulator.setVisible(true);
        });
    }
}


