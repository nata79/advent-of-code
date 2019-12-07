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
        Map<String, Integer> distances = graph.getDistances("COM");
        return distances.values().stream().reduce(Integer::sum).orElse(0);
    }

    static int getDistanceToOrbit(Graph graph, String origin, String dest) {
        return graph.getDistances(origin).get(dest) - 2;
    }

    static class Graph {
        private Map<String, List<String>> adjacentVertices;

        Graph() {
            adjacentVertices = new HashMap<>();
        }

        void addVertex(String vertex) {
            if (!adjacentVertices.containsKey(vertex))
                adjacentVertices.put(vertex, new ArrayList<>());
        }

        void addDirectedEdge(String from, String to) {
            addVertex(from);
            addVertex(to);

            adjacentVertices.get(from).add(to);
        }

        void addEdge(String from, String to) {
            addVertex(from);
            addVertex(to);

            adjacentVertices.get(from).add(to);
            adjacentVertices.get(to).add(from);
        }

        private Map<String, Integer> getDistances(String root) {
            Map<String, Integer> distances = new HashMap<>();
            distances.put(root, 0);

            Set<String> visited = new LinkedHashSet<>();

            Stack<String> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                String vertex = stack.pop();

                if (!visited.contains(vertex)) {
                    visited.add(vertex);

                    for (String adjacentVertex : this.getAdjacentVertices(vertex)) {
                        stack.push(adjacentVertex);
                        distances.put(adjacentVertex, distances.get(vertex) + 1);
                    }
                }
            }
            return distances;
        }

        private List<String> getAdjacentVertices(String label) {
            return adjacentVertices.get(label);
        }
    }
}
