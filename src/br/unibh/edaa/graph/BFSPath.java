package br.unibh.edaa.graph;

import java.util.*;

public class BFSPath {

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

    public BFSPath(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public PathResult run(int start, int target) {
        PathResult result = new PathResult();

        Queue<Integer> toProcess = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parent = new HashMap<>();

        toProcess.add(start);
        visited.add(start);
        parent.put(start, null);

        boolean found = false;

        while (!toProcess.isEmpty()) {
            int current = toProcess.poll();
            result.nodesExpanded++;

            if (current == target) {
                found = true;
                break;
            }

            // Explorar vizinhos
            for (int neighbor = 0; neighbor < adjacencyMatrix[current].length; neighbor++) {
                if (adjacencyMatrix[current][neighbor] > 0 && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    toProcess.add(neighbor);
                }
            }
        }

        // Reconstruir caminho se encontrado
        if (found) {
            result.path = reconstructPath(parent, start, target);
            result.cost = calculateCost(result.path);
        }

        return result;
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> parent, int start, int target) {
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