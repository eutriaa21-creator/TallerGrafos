import java.util.*;

public class TallerGrafos {

    // Prim
    static void prim(int[][] graph) {
        int V = graph.length;
        boolean[] mst = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < V - 1; i++) {
            int u = -1;
            for (int j = 0; j < V; j++)
                if (!mst[j] && (u == -1 || key[j] < key[u]))
                    u = j;

            mst[u] = true;

            for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && !mst[v] && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                    parent[v] = u;
                }
        }

        int total = 0;
        System.out.println("Prim:");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i);
            total += graph[i][parent[i]];
        }
        System.out.println("Costo: " + total + "\n");
    }

    // Kruskal
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u=u; this.v=v; this.w=w; }
    }

    static int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    static void kruskal(List<Edge> edges, int V) {
        edges.sort((a,b) -> a.w - b.w);

        int[] parent = new int[V];
        for (int i = 0; i < V; i++) parent[i] = i;

        int total = 0;
        System.out.println("Kruskal:");

        for (Edge e : edges) {
            int ru = find(parent, e.u);
            int rv = find(parent, e.v);

            if (ru != rv) {
                System.out.println(e.u + " - " + e.v);
                total += e.w;
                parent[ru] = rv;
            }
        }

        System.out.println("Costo: " + total + "\n");
    }

    // Bfs
    static void bfs(List<List<Integer>> adj, int start, int end) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[adj.size()];
        int[] parent = new int[adj.size()];

        q.add(start);
        visited[start] = true;
        parent[start] = -1;

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int nei : adj.get(node)) {
                if (!visited[nei]) {
                    visited[nei] = true;
                    parent[nei] = node;
                    q.add(nei);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int v = end; v != -1; v = parent[v])
            path.add(v);

        Collections.reverse(path);
        System.out.println("Bfs camino: " + path + "\n");
    }

    // Dijkstra
    static void dijkstra(int[][] graph, int src) {
        int V = graph.length;
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 0; i < V; i++) {
            int u = -1;

            for (int j = 0; j < V; j++)
                if (!visited[j] && (u == -1 || dist[j] < dist[u]))
                    u = j;

            visited[u] = true;

            for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        System.out.println("Dijkstra:");
        System.out.println(Arrays.toString(dist) + "\n");
    }

    // Floyd
    static void floyd(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++)
            dist[i] = Arrays.copyOf(graph[i], V);

        for (int k = 0; k < V; k++)
            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];

        System.out.println("Floyd:");
        for (int[] row : dist)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    // Warshall
    static void warshall(int[][] graph) {
        int V = graph.length;
        int[][] reach = new int[V][V];

        for (int i = 0; i < V; i++)
            reach[i] = Arrays.copyOf(graph[i], V);

        for (int k = 0; k < V; k++)
            for (int i = 0; i < V; i++)
                for (int j = 0; j < V; j++)
                    reach[i][j] = (reach[i][j] == 1 || (reach[i][k] == 1 && reach[k][j] == 1)) ? 1 : 0;

        System.out.println("Warshall:");
        for (int[] row : reach)
            System.out.println(Arrays.toString(row));
    }

    // Main
    public static void main(String[] args) {

        int[][] graph = {
            {0,6,1,5,0,0},
            {6,0,2,0,5,0},
            {1,2,0,2,6,0},
            {5,0,2,0,0,4},
            {0,5,6,0,0,3},
            {0,0,0,4,3,0}
        };

        prim(graph);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0,1,6));
        edges.add(new Edge(0,2,1));
        edges.add(new Edge(0,3,5));
        edges.add(new Edge(1,2,2));
        edges.add(new Edge(1,4,5));
        edges.add(new Edge(2,3,2));
        edges.add(new Edge(2,4,6));
        edges.add(new Edge(3,5,4));
        edges.add(new Edge(4,5,3));

        kruskal(edges, 6);

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 6; i++) adj.add(new ArrayList<>());

        adj.get(0).add(1); adj.get(0).add(2);
        adj.get(1).add(3);
        adj.get(2).add(3);
        adj.get(3).add(4);
        adj.get(4).add(5);

        bfs(adj, 0, 5);

        int[][] graphD = {
            {0,10,0,0,3},
            {0,0,2,0,4},
            {0,0,0,9,0},
            {0,0,7,0,0},
            {0,1,8,2,0}
        };

        dijkstra(graphD, 0);

        int INF = 9999;
        int[][] graphF = {
            {0,10,INF,INF,3},
            {INF,0,2,INF,4},
            {INF,INF,0,9,INF},
            {INF,INF,7,0,INF},
            {INF,1,8,2,0}
        };

        floyd(graphF);

        int[][] graphW = {
            {0,1,0,0},
            {0,0,1,0},
            {1,0,0,1},
            {0,0,0,0}
        };

        warshall(graphW);
    }
}
