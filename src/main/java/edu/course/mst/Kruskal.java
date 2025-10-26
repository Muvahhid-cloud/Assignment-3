package edu.course.mst;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class Kruskal {
    public static class Result {
        public List<Prim.EdgeOut> mstEdges = new ArrayList<>();
        public int totalCost = 0;
        public long operationsCount = 0;
        public double executionTimeMs = 0.0;

        JsonObject toJson(Gson gson) {
            JsonObject o = new JsonObject();
            JsonArray arr = new JsonArray();
            for (Prim.EdgeOut e : mstEdges) {
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

    private final Graph g;
    public Kruskal(Graph g) { this.g=g; }

    public Result run() {
        Result res = new Result();
        List<Edge> edges = new ArrayList<>(g.edges());
        // wrap comparator to count comparisons
        class Cmp implements Comparator<Edge> {
            public long comps = 0;
            public int compare(Edge a, Edge b) {
                comps++;
                return Integer.compare(a.weight, b.weight);
            }
        }
        Cmp cmp = new Cmp();
        edges.sort(cmp);
        long ops = cmp.comps;
        UnionFind uf = new UnionFind(g.V());
        for (Edge e : edges) {
            ops++; // decision before union
            if (uf.find(e.u) != uf.find(e.v)) {
                boolean merged = uf.union(e.u, e.v);
                ops++; // union attempt counted
                if (merged) {
                    res.mstEdges.add(new Prim.EdgeOut(g.name(e.u), g.name(e.v), e.weight));
                    res.totalCost += e.weight;
                }
            } else {
                ops++; // comparison said same set
            }
        }
        ops += uf.ops; // include union-find internal ops
        res.operationsCount = ops;
        return res;
    }
}
