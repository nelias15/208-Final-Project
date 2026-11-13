import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * KnowledgeGraph Engine for LogicForge (COSC 208 Final Project).
 *
 * Represents domain entities as vertices (nodes) and labeled directed relationships
 * as edges. Supports readable adjacency list and adjacency matrix formatting so
 * users and instructors can inspect the relational structure behind any scenario.
 */
public class KnowledgeGraph {
    
    /**
     * Represents a directed, labeled connection from a source node to a target node.
     */
    public static class Edge {
        private final String source;
        private final String target;
        private final String relationship;

        public Edge(String source, String target, String relationship) {
            this.source = source;
            this.target = target;
            this.relationship = relationship;
        }

        public String getSource() { return source; }
        public String getTarget() { return target; }
        public String getRelationship() { return relationship; }

        @Override
        public String toString() {
            return String.format("--[%s]--> %s", relationship, target);
        }
    }

    // Preserves insertion order for clean, predictable display in UI and reports
    private final Map<String, List<Edge>> adjacencyList;

    public KnowledgeGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a node (entity) to the knowledge graph if it does not already exist.
     *
     * @param nodeName Name of the entity (e.g., "User Account", "Help Desk")
     */
    public void addNode(String nodeName) {
        if (nodeName == null || nodeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Node name cannot be null or empty.");
        }
        adjacencyList.putIfAbsent(nodeName.trim(), new ArrayList<>());
    }

    /**
     * Adds a directed, labeled edge between two nodes.
     * Automatically registers missing nodes so edges never fail due to ordering.
     *
     * @param source       Starting entity
     * @param target       Destination entity
     * @param relationship Label describing how source relates to target
     */
    public void addEdge(String source, String target, String relationship) {
        if (source == null || target == null || relationship == null) {
            throw new IllegalArgumentException("Source, target, and relationship cannot be null.");
        }
        String cleanSource = source.trim();
        String cleanTarget = target.trim();
        String cleanLabel = relationship.trim();

        addNode(cleanSource);
        addNode(cleanTarget);

        // Prevent duplicate identical edges
        List<Edge> edges = adjacencyList.get(cleanSource);
        for (Edge existing : edges) {
            if (existing.getTarget().equals(cleanTarget) &&
                existing.getRelationship().equals(cleanLabel)) {
                return;
            }
        }

        edges.add(new Edge(cleanSource, cleanTarget, cleanLabel));
    }

    /**
     * Returns an unmodifiable list of all nodes in the graph.
     */
    public List<String> getNodes() {
        return Collections.unmodifiableList(new ArrayList<>(adjacencyList.keySet()));
    }

    /**
     * Returns all outgoing edges from a given node.
     */
    public List<Edge> getNeighbors(String nodeName) {
        List<Edge> edges = adjacencyList.get(nodeName);
        if (edges == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(edges);
    }

    /**
     * Returns a flat list of every directed edge in the knowledge graph.
     * Useful for JavaFX/Swing graph visualizers.
     */
    public List<Edge> getAllEdges() {
        List<Edge> allEdges = new ArrayList<>();
        for (List<Edge> edgeList : adjacencyList.values()) {
            allEdges.addAll(edgeList);
        }
        return allEdges;
    }

    /**
     * Checks whether a direct labeled or unlabeled edge exists from source to target.
     */
    public boolean hasEdge(String source, String target) {
        List<Edge> edges = adjacencyList.get(source);
        if (edges == null) {
            return false;
        }
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public int getNodeCount() {
        return adjacencyList.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<Edge> edges : adjacencyList.values()) {
            count += edges.size();
        }
        return count;
    }

    /**
     * Generates a readable Adjacency List representation of the Knowledge Graph.
     * Directly satisfies the COSC 208 knowledge graph representation requirement.
     */
    public String toAdjacencyListString() {
        if (adjacencyList.isEmpty()) {
            return "Knowledge Graph is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Knowledge Graph: Adjacency List ===\n");
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ");
            List<Edge> edges = entry.getValue();
            if (edges.isEmpty()) {
                sb.append("[No outgoing relationships]");
            } else {
                List<String> formattedEdges = new ArrayList<>();
                for (Edge e : edges) {
                    formattedEdges.add(e.toString());
                }
                sb.append(String.join(", ", formattedEdges));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Generates a formatted 0/1 Adjacency Matrix representation of the graph.
     * Bonus discrete-structures view for technical reports and presentations.
     */
    public String toAdjacencyMatrixString() {
        if (adjacencyList.isEmpty()) {
            return "Knowledge Graph is empty.";
        }

        List<String> nodes = getNodes();
        int n = nodes.size();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Knowledge Graph: Adjacency Matrix ===\n");
        sb.append("Legend:\n");
        for (int i = 0; i < n; i++) {
            sb.append(String.format("  V%d = %s%n", i, nodes.get(i)));
        }
        sb.append("\n     ");
        for (int j = 0; j < n; j++) {
            sb.append(String.format("V%-3d", j));
        }
        sb.append("\n");

        for (int i = 0; i < n; i++) {
            sb.append(String.format("V%-3d ", i));
            for (int j = 0; j < n; j++) {
                int connected = hasEdge(nodes.get(i), nodes.get(j)) ? 1 : 0;
                sb.append(String.format("%-4d", connected));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toAdjacencyListString();
    }

    /**
     * Standalone test runner demonstrating Scenario D's Knowledge Graph.
     */
    public static void main(String[] args) {
        SecureSteps simulator = new SecureSteps();
        KnowledgeGraph kg = simulator.buildKnowledgeGraph();
        System.out.println(kg.toAdjacencyListString());
        System.out.println(kg.toAdjacencyMatrixString());
    }
}
