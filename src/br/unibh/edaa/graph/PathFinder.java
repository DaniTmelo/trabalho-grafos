package br.unibh.edaa.graph;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PathFinder {

    public static class Result {
        String algorithm;
        String heuristic;
        int origin;
        int destination;
        List<Integer> path;
        int cost;
        int nodesExpanded;
        double timeMs;

        public Result(String algorithm, String heuristic, int origin, int destination) {
            this.algorithm = algorithm;
            this.heuristic = heuristic;
            this.origin = origin;
            this.destination = destination;
            this.path = new ArrayList<>();
            this.cost = 0;
            this.nodesExpanded = 0;
            this.timeMs = 0.0;
        }

        public String formatOutput() {
            StringBuilder sb = new StringBuilder();
            sb.append("ALGORITIMO: ").append(algorithm).append("\n");
            sb.append("HEURISTICA: ").append(heuristic.isEmpty() ? "" : heuristic).append("\n");
            sb.append("ORIGEM: ").append(origin).append("\n");
            sb.append("DESTINO: ").append(destination).append("\n");

            if (path.isEmpty()) {
                sb.append("CAMINHO: \n");
                sb.append("CUSTO: \n");
            } else {
                sb.append("CAMINHO: ");
                for (int i = 0; i < path.size(); i++) {
                    sb.append(path.get(i));
                    if (i < path.size() - 1) {
                        sb.append(" -> ");
                    }
                }
                sb.append("\n");
                sb.append("CUSTO: ").append(cost).append("\n");
            }

            sb.append("NOS EXPANDIDOS: ").append(nodesExpanded).append("\n");
            sb.append(String.format("TEMPO (ms): %.2f\n", timeMs));

            return sb.toString();
        }
    }

    private int[][] adjacencyMatrix;
    private String inputFileName;

    public PathFinder(String fileName) throws IOException {
        this.inputFileName = fileName;
        this.adjacencyMatrix = readMatrix(fileName);
    }

    private int[][] readMatrix(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        lines.removeIf(String::isEmpty);

        int size = lines.size();
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            String[] values = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < values.length && j < size; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
        }

        return matrix;
    }

    public void runAll(int origin, int destination) throws IOException {
        List<Result> results = new ArrayList<>();

        results.add(runBFS(origin, destination));
        results.add(runDFS(origin, destination));
        results.add(runDijkstra(origin, destination));
        results.add(runAStar(origin, destination, Heuristic.HeuristicType.MANHATTAN));
        results.add(runAStar(origin, destination, Heuristic.HeuristicType.EUCLIDEAN));
        results.add(runGreedy(origin, destination, Heuristic.HeuristicType.MANHATTAN));
        results.add(runGreedy(origin, destination, Heuristic.HeuristicType.EUCLIDEAN));

        saveResults(results);
    }

    private Result runBFS(int origin, int destination) {
        Result result = new Result("BFS", "", origin, destination);

        long startTime = System.nanoTime();
        BFSPath bfs = new BFSPath(adjacencyMatrix);
        BFSPath.PathResult pathResult = bfs.run(origin, destination);
        long endTime = System.nanoTime();

        result.path = pathResult.path;
        result.cost = pathResult.cost;
        result.nodesExpanded = pathResult.nodesExpanded;
        result.timeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    private Result runDFS(int origin, int destination) {
        Result result = new Result("DFS", "", origin, destination);

        long startTime = System.nanoTime();
        DFSPath dfs = new DFSPath(adjacencyMatrix);
        DFSPath.PathResult pathResult = dfs.run(origin, destination);
        long endTime = System.nanoTime();

        result.path = pathResult.path;
        result.cost = pathResult.cost;
        result.nodesExpanded = pathResult.nodesExpanded;
        result.timeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    private Result runDijkstra(int origin, int destination) {
        Result result = new Result("DIJKSTRA", "", origin, destination);

        long startTime = System.nanoTime();
        DijkstraPath dijkstra = new DijkstraPath(adjacencyMatrix);
        DijkstraPath.PathResult pathResult = dijkstra.run(origin, destination);
        long endTime = System.nanoTime();

        result.path = pathResult.path;
        result.cost = pathResult.cost;
        result.nodesExpanded = pathResult.nodesExpanded;
        result.timeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    private Result runAStar(int origin, int destination, Heuristic.HeuristicType heuristicType) {
        String heuristicName = heuristicType == Heuristic.HeuristicType.MANHATTAN ? "Manhattan" : "Euclidiana";
        Result result = new Result("A*", heuristicName, origin, destination);

        long startTime = System.nanoTime();
        AStarPath aStar = new AStarPath(adjacencyMatrix, origin, destination, heuristicType);
        AStarPath.PathResult pathResult = aStar.run();
        long endTime = System.nanoTime();

        result.path = pathResult.path;
        result.cost = pathResult.cost;
        result.nodesExpanded = pathResult.nodesExpanded;
        result.timeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    private Result runGreedy(int origin, int destination, Heuristic.HeuristicType heuristicType) {
        String heuristicName = heuristicType == Heuristic.HeuristicType.MANHATTAN ? "Manhattan" : "Euclidiana";
        Result result = new Result("GREEDY BEST-FIRST-SEARCH", heuristicName, origin, destination);

        long startTime = System.nanoTime();
        GreedyBestFirstPath greedy = new GreedyBestFirstPath(adjacencyMatrix, origin, destination, heuristicType);
        GreedyBestFirstPath.PathResult pathResult = greedy.run();
        long endTime = System.nanoTime();

        result.path = pathResult.path;
        result.cost = pathResult.cost;
        result.nodesExpanded = pathResult.nodesExpanded;
        result.timeMs = (endTime - startTime) / 1_000_000.0;

        return result;
    }

    private void saveResults(List<Result> results) throws IOException {
        String baseName = new File(inputFileName).getName();

        saveResult(baseName + ".bfs", results.get(0));
        saveResult(baseName + ".dfs", results.get(1));
        saveResult(baseName + ".dijkstra", results.get(2));
        saveResult(baseName + ".a.manhattan", results.get(3));
        saveResult(baseName + ".a.euclidiana", results.get(4));
        saveResult(baseName + ".gbs.manhattan", results.get(5));
        saveResult(baseName + ".gbs.euclidiana", results.get(6));
    }

    private void saveResult(String fileName, Result result) throws IOException {
        Files.writeString(Paths.get(fileName), result.formatOutput());
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java PathFinder <arquivo_entrada> <origem> <destino>");
            System.exit(1);
        }

        try {
            String inputFile = args[0];
            int origin = Integer.parseInt(args[1]);
            int destination = Integer.parseInt(args[2]);

            PathFinder finder = new PathFinder(inputFile);
            finder.runAll(origin, destination);

            System.out.println("Execução concluída! Arquivos gerados:");
            System.out.println("- " + inputFile + ".bfs");
            System.out.println("- " + inputFile + ".dfs");
            System.out.println("- " + inputFile + ".dijkstra");
            System.out.println("- " + inputFile + ".a.manhattan");
            System.out.println("- " + inputFile + ".a.euclidiana");
            System.out.println("- " + inputFile + ".gbs.manhattan");
            System.out.println("- " + inputFile + ".gbs.euclidiana");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}