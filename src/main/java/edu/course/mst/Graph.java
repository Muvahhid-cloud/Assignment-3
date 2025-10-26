package edu.course.mst;

import java.util.*;

public class Graph {
    private final int Vn;
    private final Map<String,Integer> nameToIdx = new HashMap<>();
    private final List<String> idxToName = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final List<List<Edge>> adj;

    public Graph(List<String> nodes, List<Edge> edgesInput) {
        int idx = 0;
        for (String n : nodes) {
            nameToIdx.put(n, idx++);
            idxToName.add(n);
        }
        this.Vn = nodes.size();
        for (Edge e : edgesInput) {
            int u = nameToIdx.get(e.from);
            int v = nameToIdx.get(e.to);
            Edge e2 = new Edge(u, v, e.weight);
            edges.add(e2);
        }
        adj = new ArrayList<>();
        for (int i=0;i<Vn;i++) adj.add(new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.u).add(e);
            adj.get(e.v).add(e);
        }
    }

    public int V() { return Vn; }
    public int E() { return edges.size(); }
    public List<Edge> edges() { return edges; }
    public List<List<Edge>> adj() { return adj; }
    public String name(int idx) { return idxToName.get(idx); }
    public int idxOf(String name) { return nameToIdx.get(name); }
}
