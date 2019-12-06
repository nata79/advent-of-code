import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    @Test
    void testCountOrbits() {
        Day6.Graph graph = new Day6.Graph();

        String[] orbits = {"COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L"};
        for (String orbit : orbits) {
            String[] planets = orbit.split("\\)");
            graph.addDirectedEdge(planets[0], planets[1]);
        }

        assertEquals(42, Day6.countOrbits(graph));
    }

    @Test
    void testGetDistanceToOrbit() {
        Day6.Graph graph = new Day6.Graph();

        String[] orbits = {"COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L", "K)YOU", "I)SAN"};
        for (String orbit : orbits) {
            String[] planets = orbit.split("\\)");
            graph.addEdge(planets[0], planets[1]);
        }

        assertEquals(4, Day6.getDistanceToOrbit(graph, "YOU", "SAN"));
    }
}