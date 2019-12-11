package advent.of.code.year2019;

import advent.of.code.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        char[][] grid = loadGrid("/2019/day10.txt");

        MonitoringStation monitoringStation = bestMonitoringStation(grid);
        System.out.println(String.format("Monitoring station: (%s, %s) - %s Asteroids",
                monitoringStation.getAsteroid().getX(),
                monitoringStation.getAsteroid().getY(),
                monitoringStation.getAsteroidsInSightCount()
        ));

        System.out.println(find200thKill(monitoringStation, grid));
    }

    static int find200thKill(MonitoringStation monitoringStation, char[][] grid) {
        List<Double> sortedAngles = monitoringStation.getAsteroidsInSight()
                .keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());

        double start = -90 * Math.PI / 180;
        List<Double> anglesStartingUp = new ArrayList<>();

        for (Double d : sortedAngles)
            if (d >= start)
                anglesStartingUp.add(d);

        for (Double d : sortedAngles)
            if (d < start)
                anglesStartingUp.add(d);

        int count = 0;

        while (true) {
            for (Double angle : anglesStartingUp) {
                TreeSet<Asteroid> asteroids = monitoringStation.getAsteroidsInSight().get(angle);

                if (!asteroids.isEmpty()) {
                    Asteroid p = asteroids.first();
                    asteroids.remove(p);
                    count++;

                    if (count == 200) {
                        return p.x * 100 + p.y;
                    }
                }
            }
        }
    }

    private static double distance(Asteroid p, int x, int y) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    static MonitoringStation bestMonitoringStation(char[][] grid) {
        MonitoringStation monitoringStation = null;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#') {
                    Map<Double, TreeSet<Asteroid>> asteroidsInSight = countAsteroidsInSight(grid, i, j);
                    if (monitoringStation == null || asteroidsInSight.size() > monitoringStation.getAsteroidsInSightCount()) {
                        monitoringStation = new MonitoringStation(new Asteroid(i, j), asteroidsInSight);
                    }
                }
            }
        }

        return monitoringStation;
    }

    private static Map<Double, TreeSet<Asteroid>> countAsteroidsInSight(char[][] grid, int y, int x) {
        Map<Double, TreeSet<Asteroid>> directions = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                // An asteroid can't see itself
                if (!(i == x && j == y)) {

                    // Check if point is an Asteroid
                    if (grid[i][j] == '#') {
                        double angle = Math.atan2(i - y, j - x);

                        if (directions.containsKey(angle))
                            directions.get(angle).add(new Asteroid(j, i));
                        else {
                            TreeSet<Asteroid> asteroids = new TreeSet<>(
                                    Comparator.comparingDouble(asteroid -> distance(asteroid, x, y))
                            );

                            asteroids.add(new Asteroid(j, i));
                            directions.put(angle, asteroids);
                        }
                    }
                }
            }
        }

        return directions;
    }

    private static char[][] loadGrid(String filepath) {
        return loadGrid(filepath, Day10.class);
    }

    static char[][] loadGrid(String filepath, Class klass) {
        String input = Utils.readFile(filepath, klass);
        return Arrays.stream(input.split("\n"))
                .map(String::toCharArray)
                .collect(Collectors.toList())
                .toArray(char[][]::new);
    }

    static class Asteroid {
        private final int x;
        private final int y;

        Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Asteroid asteroid = (Asteroid) o;
            return x == asteroid.x &&
                    y == asteroid.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class MonitoringStation {
        private final Asteroid asteroid;
        private final Map<Double, TreeSet<Asteroid>> asteroidsInSight;

        MonitoringStation(Asteroid asteroid, Map<Double, TreeSet<Asteroid>> asteroidsInSight) {
            this.asteroid = asteroid;
            this.asteroidsInSight = asteroidsInSight;
        }

        Asteroid getAsteroid() {
            return asteroid;
        }

        Map<Double, TreeSet<Asteroid>> getAsteroidsInSight() {
            return asteroidsInSight;
        }

        int getAsteroidsInSightCount() {
            return asteroidsInSight.size();
        }
    }
}
