package advent.of.code.year2019;

import advent.of.code.year2019.intcode.IntCodeBlockingIOExecutor;
import advent.of.code.year2019.intcode.IntCodeProgram;

import java.util.*;

public class Day15 {
    public static void main(String[] args) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day15.txt");
        IntCodeBlockingIOExecutor executor = program.getBlockingIOExecutor();
        
        int[] currentPosition = new int[]{0, 0};
        long lastMovement = 1L;

        Day6.Graph graph = new Day6.Graph();
        graph.addVertex(Arrays.toString(currentPosition));

        executor.provideInput(lastMovement);
        int steps = 2_000_000;
        while (executor.isRunning() && steps > 0) {
            Queue<Long> output = executor.runUntilInputNeeded();
            long state = output.remove();

            if (state != 0) {
                graph.addEdge(
                        Arrays.toString(currentPosition),
                        Arrays.toString(nextPosition(currentPosition, lastMovement))
                );

                currentPosition = nextPosition(currentPosition, lastMovement);
            }

            lastMovement = new Random().nextInt(4) + 1;
            executor.provideInput(lastMovement);
            steps--;
        }


        System.out.println(graph.getDistances(Arrays.toString(new int[]{0, 0})).get(Arrays.toString(new int[]{18, 18})));
        System.out.println(graph.getDistances(Arrays.toString(new int[]{18, 18})).values().stream().max(Integer::compareTo));
    }

    private static int[] nextPosition(int[] currentPosition, long movement) {
        switch (Long.valueOf(movement).intValue()) {
            case 1:
                return new int[]{currentPosition[0], currentPosition[1] + 1};
            case 2:
                return new int[]{currentPosition[0], currentPosition[1] - 1};
            case 3:
                return new int[]{currentPosition[0] - 1, currentPosition[1]};
            case 4:
                return new int[]{currentPosition[0] + 1, currentPosition[1]};
        }

        return currentPosition;
    }
}
