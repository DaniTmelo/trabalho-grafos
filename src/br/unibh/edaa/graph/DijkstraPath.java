package br.unibh.edaa.graph;

import java.util.*;

public class DijkstraPath {

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
        private int distance = Integer.MAX_VALUE;
        private Vertex parent;

        public Vertex(int number, Map<Integer, Integer> edges) {
            this.number = number;
            this.edges = edges;
        }

        public int getNumber() {
            return number;
        }

        public Map<Integer, Integer> getEdges() {
            return edges;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        public Vertex getParent() {
            return parent;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    private final Map<Integer, Vertex> vertexMap;

    public DijkstraPath(int[][] adjacentMatrix) {
        vertexMap = new HashMap<>();

        for (int i = 0; i < adjacentMatrix.length; i++) {
            Map<Integer, Integer> edges = new HashMap<>();

            for (int j = 0; j < adjacentMatrix[i].length; j++) {
                if (adjacentMatrix[i][j] > 0) {
                    edges.put(j, adjacentMatrix[i][j]);
                }
            }

            vertexMap.put(i, new Vertex(i, edges));
        }
    }

    public PathResult run(int start, int target) {
        PathResult result = new PathResult();

        vertexMap.get(start).setDistance(0);

        PriorityQueue<Vertex> toProcess = new PriorityQueue<>();
        toProcess.add(vertexMap.get(start));

        Set<Integer> visited = new HashSet<>();

        while (!toProcess.isEmpty()) {
            Vertex currentVertex = toProcess.poll();

            if (visited.contains(currentVertex.getNumber())) {
                continue;
            }

            visited.add(currentVertex.getNumber());
            result.nodesExpanded++;

            // Se chegou no destino, pode parar
            if (currentVertex.getNumber() == target) {
                break;
            }

            for (Map.Entry<Integer, Integer> currentEdge : currentVertex.getEdges().entrySet()) {
                int weight = currentEdge.getValue();
                Vertex neighbor = vertexMap.get(currentEdge.getKey());

                if (!visited.contains(neighbor.getNumber())) {
                    int novaDistancia = currentVertex.getDistance() + weight;
                    if (novaDistancia < neighbor.getDistance()) {
                        neighbor.setDistance(novaDistancia);
                        neighbor.setParent(currentVertex);
                        toProcess.add(neighbor);
                    }
                }
            }
        }

        // Reconstruir caminho
        Vertex targetVertex = vertexMap.get(target);
        if (targetVertex.getDistance() != Integer.MAX_VALUE) {
            result.path = reconstructPath(targetVertex);
            result.cost = targetVertex.getDistance();
        }

        return result;
    }

    private List<Integer> reconstructPath(Vertex target) {
        List<Integer> path = new ArrayList<>();
        Vertex current = target;

        while (current != null) {
            path.add(current.getNumber());
            current = current.getParent();
        }

        Collections.reverse(path);
        return path;
    }
}