package edu.course.mst;

public class Edge {
    public final int u, v;
    public final int weight;
    public final String from, to; // optional convenience

    // constructor for parsed graph (by name)
    public Edge(String from, String to, int weight) {
        this.from = from; this.to = to; this.weight = weight;
        this.u = -1; this.v = -1;
    }
    // constructor for internal numeric graph
    public Edge(int u, int v, int weight) {
        this.u = u; this.v = v; this.weight = weight;
        this.from = null; this.to = null;
    }
}
