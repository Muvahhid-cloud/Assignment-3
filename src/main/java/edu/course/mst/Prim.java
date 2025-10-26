package edu.course.mst;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class Prim {
    public static class Result {
        public List<EdgeOut> mstEdges = new ArrayList<>();
        public int totalCost = 0;
        public long operationsCount = 0;
        public double executionTimeMs = 0.0;

        JsonObject toJson(Gson gson) {
            JsonObject o = new JsonObject();
            JsonArray arr = new JsonArray();
            for (EdgeOut e : mstEdges) {
                JsonObject eo = new JsonObject();
                eo.addProperty("from", e.from);
                eo.addProperty("to", e.to);
                eo.addProperty("weight", e.weight);
                arr.add(eo);
            }
            o.add("mst_edges", arr);
            o.addProperty("total_cost", totalCost);
            o.addProperty("operations_count", operationsCount);
            o.addProperty("execution_time_ms", executionTimeMs);
            return o;
        }
    }
    public static class EdgeOut { public String from, to; public int weight; public EdgeOut(String f,String t,int w){from=f;to=t;weight=w;} }

    private final Graph g;
    public Prim(Graph g) { this.g=g; }

    public Result run() {
        int V = g.V();
        Result res = new Result();
        boolean[] used = new boolean[V];
        int[] minEdge = new int[V];
        int[] selEdge = new int[V];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        Arrays.fill(selEdge, -1);
        minEdge[0]=0;
        // we'll implement simple O(V^2) Prim to make operations counting straightforward
        long ops=0;
        for (int i=0;i<V;i++) {
            int v=-1;
            for (int j=0;j<V;j++) {
                ops++; // comparison to pick min
                if (!used[j] && (v==-1 || minEdge[j]<minEdge[v])) v=j;
            }
            if (minEdge[v]==Integer.MAX_VALUE) break;
            used[v]=true;
            if (selEdge[v]!=-1) {
                int u = selEdge[v];
                res.mstEdges.add(new EdgeOut(g.name(u), g.name(v), minEdge[v]));
                res.totalCost += minEdge[v];
            }
            for (Edge e : g.adj().get(v)) {
                int to = e.u==v ? e.v : e.u;
                ops++; // comparison for neighbor weight check
                if (e.weight < minEdge[to]) {
                    minEdge[to]=e.weight;
                    selEdge[to]=v;
                    ops++; // update key
                }
            }
        }
        res.operationsCount = ops;
        return res;
    }
}
