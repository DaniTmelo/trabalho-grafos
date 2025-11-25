package br.unibh.edaa.graph;

import java.util.*;

public class GreedyBestFirstPath {

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

    public static class Vertex implements Comparable<Vertex> {
        private final int number;
        private final Map<Integer, Integer> edges;
        private final double heuristic;
        private Vertex parent;

        public Vertex(int number, double heuristic, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
            this.heuristic = heuristic;
        }

        public int getNumber() {
            return number;
        }

        public Vertex getParent() {
            return parent;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        public Map<Integer, Integer> getEdges() {
            return edges;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.heuristic, other.heuristic);
        }
    }

    private final Map<Integer, Vertex> vertexMap;
    private final int[][] adjacencyMatrix;
    private final int start;
    private final int target;

    public GreedyBestFirstPath(int[][] adjacentMatrix, int start, int target, Heuristic.HeuristicType heuristicType) {
        this.start = start;
        this.target = target;
        this.adjacencyMatrix = adjacentMatrix;

        Map<Integer, Double> distances;
        if (heuristicType == Heuristic.HeuristicType.EUCLIDEAN) {
            distances = Heuristic.euclideanDistances(adjacentMatrix, target);
        } else {
            distances = Heuristic.manhattanDistances(adjacentMatrix, target);
        }

        vertexMap = new HashMap<>();
        for (int i = 0; i < adjacentMatrix.length; i++) {
            Map<Integer, Integer> edges = new HashMap<>();

            for (int j = 0; j < adjacentMatrix[i].length; j++) {
                if (adjacentMatrix[i][j] > 0) {
                    edges.put(j, adjacentMatrix[i][j]);
                }
            }

            vertexMap.put(i, new Vertex(i, distances.get(i), edges));
        }
    }

    public PathResult run() {
        PathResult result = new PathResult();

        PriorityQueue<Vertex> open = new PriorityQueue<>();
        open.add(vertexMap.get(start));

        Set<Integer> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Vertex current = open.poll();

            if (closed.contains(current.getNumber())) continue;

            closed.add(current.getNumber());
            result.nodesExpanded++;

            if (current.getNumber() == target) {
                result.path = reconstructPath(current);
                result.cost = calculateCost(result.path);
                return result;
            }

            for (Map.Entry<Integer, Integer> entry : current.getEdges().entrySet()) {
                Vertex neighbor = vertexMap.get(entry.getKey());

                if (!closed.contains(neighbor.getNumber())) {
                    neighbor.setParent(current);
                    open.add(neighbor);
                }
            }
        }

        return result;
    }

    private List<Integer> reconstructPath(Vertex current) {
        List<Integer> path = new ArrayList<>();
        while (current != null) {
            path.add(current.getNumber());
            current = current.getParent();
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