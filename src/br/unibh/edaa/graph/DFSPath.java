package br.unibh.edaa.graph;

import java.util.*;

public class DFSPath {

    public static class PathResult {
        public List<Integer> path;
        public int cost;
        public int nodesExpanded;

        public PathResult() {
            this.path = new ArrayList<>();
            this.cost = 0;
            this.nodesExpanded = 0;
        }
    }

    private final int[][] adjacencyMatrix;
    private Set<Integer> visited;
    private Map<Integer, Integer> parent;
    private int target;
    private int nodesExpanded;
    private boolean found;

    public DFSPath(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public PathResult run(int start, int target) {
        PathResult result = new PathResult();

        this.visited = new HashSet<>();
        this.parent = new HashMap<>();
        this.target = target;
        this.nodesExpanded = 0;
        this.found = false;

        parent.put(start, null);
        dfs(start);

        result.nodesExpanded = this.nodesExpanded;

        // Reconstruir caminho se encontrado
        if (found) {
            result.path = reconstructPath(start, target);
            result.cost = calculateCost(result.path);
        }

        return result;
    }

    private void dfs(int node) {
        if (found) return;

        visited.add(node);
        nodesExpanded++;

        if (node == target) {
            found = true;
            return;
        }

        // Explorar vizinhos
        for (int neighbor = 0; neighbor < adjacencyMatrix[node].length; neighbor++) {
            if (adjacencyMatrix[node][neighbor] > 0 && !visited.contains(neighbor)) {
                parent.put(neighbor, node);
                dfs(neighbor);
                if (found) return;
            }
        }
    }

    private List<Integer> reconstructPath(int start, int target) {
        List<Integer> path = new ArrayList<>();
        Integer current = target;

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    private int calculateCost(List<Integer> path) {
        if (path.size() <= 1) return 0;

        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            cost += adjacencyMatrix[from][to];
        }

        return cost;
    }
}