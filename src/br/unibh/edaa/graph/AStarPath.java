package br.unibh.edaa.graph;

import java.util.*;

public class AStarPath {

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
        private double distance = Double.POSITIVE_INFINITY;
        private Vertex parent;

        public Vertex(int number, double heuristic, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
            this.heuristic = heuristic;
        }

        public int getNumber() {
            return this.number;
        }

        public Vertex getParent() {
            return this.parent;
        }

        public Map<Integer, Integer> getEdges() {
            return this.edges;
        }

        public double f() {
            return this.heuristic + this.distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getDistance() {
            return this.distance;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.f(), other.f());
        }
    }

    private final Map<Integer, Vertex> vertexMap;
    private final int start;
    private final int target;

    public AStarPath(int[][] adjacentMatrix, int start, int target, Heuristic.HeuristicType heuristicType) {
        this.start = start;
        this.target = target;

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

        vertexMap.get(this.start).setDistance(0);

        PriorityQueue<Vertex> open = new PriorityQueue<>();
        open.add(vertexMap.get(this.start));

        Set<Integer> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Vertex currentVertex = open.poll();

            if (closed.contains(currentVertex.getNumber())) continue;

            closed.add(currentVertex.getNumber());
            result.nodesExpanded++;

            if (currentVertex.getNumber() == target) {
                result.path = reconstructPath(currentVertex);
                result.cost = (int) currentVertex.getDistance();
                return result;
            }

            for (Map.Entry<Integer, Integer> currentEdge : currentVertex.getEdges().entrySet()) {
                int weight = currentEdge.getValue();
                Vertex neighbor = vertexMap.get(currentEdge.getKey());

                if (!closed.contains(neighbor.getNumber())) {
                    double tentativeG = currentVertex.distance + weight;

                    if (tentativeG < neighbor.distance) {
                        neighbor.setDistance(tentativeG);
                        neighbor.setParent(currentVertex);
                        open.add(neighbor);
                    }
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
}