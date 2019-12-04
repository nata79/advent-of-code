import java.util.*;
import java.util.function.Function;

public class Day3 {
    public static void main(String[] args) {
        String input = Utils.readFile("day3.txt");

        String[] lines = input.split("\n");

        String[] line1 = lines[0].split(",");
        String[] line2 = lines[1].split(",");

        System.out.println(closestIntersection(line1, line2));
        System.out.println(minStepsIntersection(line1, line2));
    }

    static int closestIntersection(String[] line1, String[] line2) {
        return minIntersection(
                line1,
                line2,
                intersection -> Math.abs(intersection.getPoint().getX()) + Math.abs(intersection.getPoint().getY())
        );
    }

    static int minStepsIntersection(String[] line1, String[] line2) {
        return minIntersection(
                line1,
                line2,
                intersection -> intersection.getLine1Steps() + intersection.getLine2Steps()
        );
    }

    private static int minIntersection(String[] line1, String[] line2, Function<Intersection, Integer> mapFunction) {
        Map<Point, Integer> line1Points = getPoints(line1);
        Map<Point, Integer> line2Points = getPoints(line2);

        List<Intersection> intersections = new ArrayList<>();

        line1Points.forEach((key, value) -> {
            if (line2Points.containsKey(key))
                intersections.add(new Intersection(key, value, line2Points.get(key)));
        });

        return intersections.stream()
                .map(mapFunction)
                .min(Integer::compareTo)
                .orElse(0);
    }

    private static Map<Point, Integer> getPoints(String[] line) {
        Map<Point, Integer> points = new HashMap<>();
        Point currentPoint = new Point(0, 0);
        int steps = 0;

        for (String movement : line) {
            String direction = movement.substring(0, 1);
            int distance = Integer.parseInt(movement.substring(1));

            for (int i = 0; i < distance; i++) {
                switch (direction) {
                    case "U":
                        currentPoint = new Point(currentPoint.getX(), currentPoint.getY() + 1);
                        break;
                    case "D":
                        currentPoint = new Point(currentPoint.getX(), currentPoint.getY() - 1);
                        break;
                    case "L":
                        currentPoint = new Point(currentPoint.getX() - 1, currentPoint.getY());
                        break;
                    case "R":
                        currentPoint = new Point(currentPoint.getX() + 1, currentPoint.getY());
                        break;
                }

                steps++;

                if (!points.containsKey(currentPoint))
                    points.put(currentPoint, steps);
            }
        }

        return points;
    }

    private static class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
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
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

    private static class Intersection {
        private final Point point;
        private final int line1Steps;
        private final int line2Steps;

        Intersection(Point point, int line1Steps, int line2Steps) {
            this.point = point;
            this.line1Steps = line1Steps;
            this.line2Steps = line2Steps;
        }

        Point getPoint() {
            return point;
        }

        int getLine1Steps() {
            return line1Steps;
        }

        int getLine2Steps() {
            return line2Steps;
        }
    }
}
