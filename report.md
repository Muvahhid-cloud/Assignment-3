# Assignment 3

## Objective
The purpose of this assignment is to apply **Prim’s** and **Kruskal’s** algorithms to optimize a city’s transportation network.  
The goal is to determine the **minimum-cost set of roads** that connect all districts while ensuring full connectivity across the city.

This scenario is modeled as a **weighted, undirected graph**, where:
- **Vertices** represent city districts.
- **Edges** represent potential roads between districts.
- **Edge weights** represent construction costs.

---

## Summary of Input Data
The input data for the transportation network is provided in `input/graphs.json`.

Three sample graphs are included to represent **different scales** of a city network:

| Graph | Vertices | Edges | Description |
|:------|:----------|:------|:-------------|
| Graph 1 | 5 (A, B, C, D, E) | 7 | Small-scale district network |
| Graph 2 | 10 (A–J) | 15 | Medium-sized city layout |
| Graph 3 | 20+ | 30+ | Large city network for performance comparison |

Each graph is processed individually, and results are saved to `output/results.json`.

---

## Algorithm Results (Example Run)
When the program executes, it displays both algorithms’ outcomes in the console and writes them to the results file.

**Each result includes:**
- The **list of edges** forming the MST
- The **total construction cost** (sum of edge weights)
- The **number of vertices and edges** in the graph
- The **operation count** (comparisons, unions, updates)
- The **execution time** in milliseconds

## Project Structure
```
assignment3-mst-optimization/
│
├── input/
│   └── graphs.json                     # Input graphs defining city networks
│
├── output/
│   └── results.json                    # Generated MST results for all graphs
│
├── src/
│   ├── main/
│      └── java/
│          └── edu/
│              └── course/
│                  └── mst/
│                      ├── Edge.java           # Represents an edge with weight
│                      ├── Graph.java          # Graph data structure (nodes + adjacency)
│                      ├── Prim.java           # Prim's MST algorithm implementation
│                      ├── Kruskal.java        # Kruskal's MST algorithm implementation
│                      ├── UnionFind.java      # Disjoint-set structure for Kruskal
│                      └── Main.java           # Entry point (reads input & writes results)
│
│
├── report.md                        # Detailed explanation & analysis
│
├── README.md                            # Project documentation
└── pom.xml
```

## Efficiency and Performance Comparison

| Algorithm | Time Complexity | Key Structure | Best For | Implementation Type |
|:-----------|:----------------|:---------------|:----------|:--------------------|
| **Prim’s Algorithm** | O(V²) | Adjacency Matrix/List | Dense Graphs | Deterministic, Simple Variant |
| **Kruskal’s Algorithm** | O(E log E) | Union-Find | Sparse Graphs | Edge-Sorted Implementation |

### Prim’s Algorithm
- Iteratively grows the MST by choosing the smallest edge that connects a new vertex.
- Effective for **dense graphs**, where most vertices are interconnected.
- The implemented version uses a simple **O(V²)** loop for clarity (no heap-based optimization).
- Operation counting is based on **vertex comparisons** and **neighbor updates**.

### Kruskal’s Algorithm
- Sorts all edges by cost and connects them while avoiding cycles using **Disjoint Sets (Union-Find)**.
- Performs efficiently on **sparse graphs**.
- Operation counting tracks **edge comparisons**, **unions**, and **find operations**.

---

## Observations from Experimental Data

- Both algorithms produce **identical MST total costs**, as expected.
- **Prim’s Algorithm** performs more node-level comparisons in dense networks.
- **Kruskal’s Algorithm** has a faster runtime on sparse graphs due to efficient sorting.
- For larger graphs, Kruskal tends to scale better in terms of both operations and time.

| Graph | MST Cost | Faster Algorithm | Remarks |
|:------|:----------|:-----------------|:---------|
| Graph 1 | 16 | Equal | Small graph, similar performance |
| Graph 2 | 35 | Kruskal | Fewer edges → faster sort & union-find |
| Graph 3 | 62 | Kruskal | Sparse structure favored edge-based processing |

---

## Conclusions — When to Use Which Algorithm

- **Use Prim’s Algorithm** when:
    - The graph is **dense** (many connections).
    - You already maintain an **adjacency structure**.
    - You need a simple, deterministic implementation.

- **Use Kruskal’s Algorithm** when:
    - The graph is **sparse**.
    - You have edges in list form or can easily sort them.
    - You prefer simpler **Union-Find logic** for cycle detection.

**In summary:**  
Both algorithms achieve the same MST cost.  
Kruskal is generally **faster and simpler** for real-world city networks with selective road connections, while Prim is **easier to adapt** for dense connectivity cases.

---

