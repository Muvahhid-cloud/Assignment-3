package edu.course.mst;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputPath = "input/graphs.json";
        if (args.length > 0) inputPath = args[0];
        String input = new String(Files.readAllBytes(Paths.get(inputPath)));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = gson.fromJson(input, JsonObject.class);
        JsonArray graphs = root.getAsJsonArray("graphs");

        JsonObject resultsRoot = new JsonObject();
        JsonArray resultsArr = new JsonArray();

        for (JsonElement gEl : graphs) {
            JsonObject g = gEl.getAsJsonObject();
            int id = g.get("id").getAsInt();
            JsonArray nodesJson = g.getAsJsonArray("nodes");
            List<String> nodes = new ArrayList<>();
            for (JsonElement n : nodesJson) nodes.add(n.getAsString());
            JsonArray edgesJson = g.getAsJsonArray("edges");
            List<Edge> edges = new ArrayList<>();
            for (JsonElement eEl : edgesJson) {
                JsonObject eo = eEl.getAsJsonObject();
                edges.add(new Edge(eo.get("from").getAsString(), eo.get("to").getAsString(), eo.get("weight").getAsInt()));
            }

            Graph graph = new Graph(nodes, edges);

            // Prim
            Prim prim = new Prim(graph);
            long start = System.nanoTime();
            Prim.Result primRes = prim.run();
            long end = System.nanoTime();
            primRes.executionTimeMs = (end - start) / 1_000_000.0;

            // Kruskal
            Kruskal kr = new Kruskal(graph);
            start = System.nanoTime();
            Kruskal.Result krRes = kr.run();
            end = System.nanoTime();
            krRes.executionTimeMs = (end - start) / 1_000_000.0;

            // Console Output (human-friendly)
            printGraphResults(id, graph, primRes, krRes);

            // Build JSON entry
            JsonObject entry = new JsonObject();
            entry.addProperty("graph_id", id);
            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", graph.V());
            inputStats.addProperty("edges", graph.E());
            entry.add("input_stats", inputStats);

            entry.add("prim", primRes.toJson(gson));
            entry.add("kruskal", krRes.toJson(gson));

            resultsArr.add(entry);
        }

        resultsRoot.add("results", resultsArr);
        // write results to file
        Files.createDirectories(Paths.get("output"));
        try (FileWriter fw = new FileWriter("output/results.json")) {
            gson.toJson(resultsRoot, fw);
        }
        System.out.println("Results written to output/results.json");
    }

    private static void printGraphResults(int id, Graph graph, Prim.Result primRes, Kruskal.Result krRes) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Graph ID: " + id);
        System.out.println("Vertices: " + graph.V() + ", Edges: " + graph.E());
        System.out.println();

        System.out.println("Prim's MST:");
        System.out.println("  Edges:");
        for (Prim.EdgeOut e : primRes.mstEdges) {
            System.out.printf("    %s - %s (w=%d)%n", e.from, e.to, e.weight);
        }
        System.out.println("  Total cost: " + primRes.totalCost);
        System.out.println("  Operations: " + primRes.operationsCount);
        System.out.printf("  Execution time: %.3f ms%n", primRes.executionTimeMs);
        System.out.println();

        System.out.println("Kruskal's MST:");
        System.out.println("  Edges:");
        for (Prim.EdgeOut e : krRes.mstEdges) {
            System.out.printf("    %s - %s (w=%d)%n", e.from, e.to, e.weight);
        }
        System.out.println("  Total cost: " + krRes.totalCost);
        System.out.println("  Operations: " + krRes.operationsCount);
        System.out.printf("  Execution time: %.3f ms%n", krRes.executionTimeMs);
        System.out.println("-----------------------------------------------------");
        System.out.println();
    }
}
