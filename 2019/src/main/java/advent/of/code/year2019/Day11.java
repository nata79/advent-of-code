package advent.of.code.year2019;

import advent.of.code.year2019.intcode.IntCodeBlockingIOExecutor;
import advent.of.code.year2019.intcode.IntCodeProgram;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class Day11 {
    public static void main(String[] args) throws IOException {
        Map<Point, Long> board = runRobot(0L);
        System.out.println(board.size());

        board = runRobot(1L);
        printBoard(board);
    }

    private static void printBoard(Map<Point, Long> board) throws IOException {
        int minX = board.keySet().stream().map(Point::getX).min(Integer::compareTo).orElse(0);
        int maxX = board.keySet().stream().map(Point::getX).max(Integer::compareTo).orElse(0);

        int minY = board.keySet().stream().map(Point::getY).min(Integer::compareTo).orElse(0);
        int maxY = board.keySet().stream().map(Point::getY).max(Integer::compareTo).orElse(0);

        BufferedImage bufferedImage = new BufferedImage(Math.abs(maxX - minX), Math.abs(maxY - minY) + 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        for (Map.Entry<Point, Long> entry : board.entrySet()) {
            g2d.setColor(entry.getValue() == 1L ? Color.WHITE : Color.BLACK);
            g2d.fillRect(entry.getKey().getX() - minX, entry.getKey().getY() - minY, 1, 1);
        }

        File file = new File("day11.png");
        ImageIO.write(bufferedImage, "png", file);
    }

    private static Map<Point, Long> runRobot(long defaultColour) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day11.txt");
        IntCodeBlockingIOExecutor executor = program.getBlockingIOExecutor();

        executor.provideInput(defaultColour);

        Position position = new Position();
        Map<Point, Long> board = new HashMap<>();

        while (executor.isRunning()) {
            Queue<Long> output = executor.runUntilInputNeeded();

            Long colour = output.remove();
            Long directionChange = output.remove();

            board.put(new Point(position.getCurrentX(), position.getCurrentY()), colour);

            position.changeDirection(directionChange);
            position.move();

            executor.provideInput(
                    board.getOrDefault(new Point(position.getCurrentX(), position.getCurrentY()), 0L)
            );
        }
        return board;
    }

    private static class Position {
        private int currentX;
        private int currentY;
        private char currentDirection;

        Position() {
            currentX = 0;
            currentY = 0;
            currentDirection = 'u';
        }

        int getCurrentX() {
            return currentX;
        }

        int getCurrentY() {
            return currentY;
        }

        void changeDirection(long direction) {
            switch (currentDirection) {
                case 'u':
                    currentDirection = direction == 0 ? 'l' : 'r';
                    break;
                case 'r':
                    currentDirection = direction == 0 ? 'u' : 'd';
                    break;
                case 'd':
                    currentDirection = direction == 0 ? 'r' : 'l';
                    break;
                case 'l':
                    currentDirection = direction == 0 ? 'd' : 'u';
                    break;
            }
        }

        void move() {
            switch (currentDirection) {
                case 'u':
                    currentY--;
                    break;
                case 'r':
                    currentX++;
                    break;
                case 'd':
                    currentY++;
                    break;
                case 'l':
                    currentX--;
                    break;
            }
        }
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
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
