package advent.of.code.year2019;

import advent.of.code.year2019.intcode.IntCodeBlockingIOExecutor;
import advent.of.code.year2019.intcode.IntCodeProgram;

import java.util.Arrays;
import java.util.Queue;

public class Day13 {
    public static void main(String[] args) {
        IntCodeProgram program = IntCodeProgram.load("/2019/day13.txt");
        program.overrideInstruction(0, 2L);

        IntCodeBlockingIOExecutor executor = program.getBlockingIOExecutor();

        long ballMovements = 0;
        long score = 0;
        int blocks = 0;

        long[] barPosition = new long[]{0, 0};
        long[] ballPosition = new long[]{0, 0};
        long[] previousBallPosition = new long[]{0, 0};

        while (executor.isRunning()) {
            Queue<Long> output = executor.runUntilInputNeeded();

            while (!output.isEmpty()) {
                long x = output.remove();
                long y = output.remove();
                long tileId = output.remove();

                if (x == -1)
                    score = tileId;
                else {
                    switch (Long.valueOf(tileId).intValue()) {
                        case 2:
                            blocks++;
                            break;
                        case 3:
                            barPosition[0] = x;
                            barPosition[1] = y;
                            break;
                        case 4:
                            ballMovements++;
                            previousBallPosition = Arrays.copyOf(ballPosition, ballPosition.length);
                            ballPosition[0] = x;
                            ballPosition[1] = y;
                            break;
                    }
                }
            }

            if (ballPosition[1] > barPosition[1])
                throw new RuntimeException("Lost the game :(");

            long steps = barPosition[1] - ballPosition[1];

            if (ballPosition[0] > previousBallPosition[0]) {
                long impactX = ballPosition[0] + steps - 1;

                if (impactX == barPosition[0])
                    executor.provideInput(0L);
                else if (impactX < barPosition[0])
                    executor.provideInput(-1L);
                else
                    executor.provideInput(1L);
            } else {
                long impactX = ballPosition[0] - steps - 1;

                if (impactX == barPosition[0])
                    executor.provideInput(0L);
                else if (impactX < barPosition[0])
                    executor.provideInput(-1L);
                else
                    executor.provideInput(1L);
            }
        }

        System.out.println("Score: " + score);
        System.out.println("Blocks: " + blocks);
    }
}
