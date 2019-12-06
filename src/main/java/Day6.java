import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        String input = Utils.readFile("day6.txt");
        String[] orbits = input.split("\n");

        Graph graph = new Graph();
        Graph graph1 = new Graph();

        for (String orbit : orbits) {
            String[] planets = orbit.split("\\)");
            graph.addDirectedEdge(planets[0], planets[1]);
            graph1.addEdge(planets[0], planets[1]);
        }

        System.out.println(countOrbits(graph));
        System.out.println(getDistanceToOrbit(graph1, "YOU", "SAN"));
    }

    static int countOrbits(Graph graph) {
        Map<String, Integer> distances = getDistances(graph, "COM");
        return distances.values().stream().reduce(Integer::sum).orElse(0);
    }

    static int getDistanceToOrbit(Graph graph, String origin, String dest) {
        return getDistances(graph, origin).get(dest) - 2;
    }

    private static Map<String, Integer> getDistances(Graph graph, String root) {
        Map<String, Integer> distances = new HashMap<>();
        Set<String> visited = new LinkedHashSet<>();

        Stack<String> stack = new Stack<>();
        stack.push(root);
        distances.put(root, 0);

        while (!stack.isEmpty()) {
            String vertex = stack.pop();

            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex v : graph.getAdjacentVertices(vertex)) {
                    stack.push(v.label);
                    distances.put(v.label, distances.get(vertex) + 1);
                }
            }
        }
        return distances;
    }

    static class Vertex {
        private final String label;

        Vertex(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return label.equals(vertex.getLabel());
        }

        @Override
        public int hashCode() {
            return label.hashCode();
        }
    }

    static class Graph {
        private Map<Vertex, List<Vertex>> adjacentVertices;

        Graph() {
            adjacentVertices = new HashMap<>();
        }

        void addVertex(Vertex vertex) {
            if (!adjacentVertices.containsKey(vertex))
                adjacentVertices.put(vertex, new ArrayList<>());
        }

        void addDirectedEdge(String from, String to) {
            Vertex v1 = new Vertex(from);
            Vertex v2 = new Vertex(to);

            addVertex(v1);
            addVertex(v2);

            adjacentVertices.get(v1).add(v2);
        }

        void addEdge(String from, String to) {
            Vertex v1 = new Vertex(from);
            Vertex v2 = new Vertex(to);

            addVertex(v1);
            addVertex(v2);

            adjacentVertices.get(v1).add(v2);
            adjacentVertices.get(v2).add(v1);
        }

        List<Vertex> getAdjacentVertices(String label) {
            return adjacentVertices.get(new Vertex(label));
        }
    }
}
