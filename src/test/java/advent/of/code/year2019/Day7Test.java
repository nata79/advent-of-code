package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    @Test
    void testRunPipeline() {
        Integer[] program = new Integer[]{3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0};
        int[] sequence = new int[]{4, 3, 2, 1, 0};

        assertEquals(Day7.runPipeline(sequence, program), 43210);

        program = new Integer[]{3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33, 1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0};
        sequence = new int[]{1, 0, 4, 3, 2};

        assertEquals(65210, Day7.runPipeline(sequence, program));
    }

    @Test
    void testFindMaxSignal() {
        Integer[] program = new Integer[]{3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0};
        assertEquals(43210, Day7.findMaxSignal(program));

        program = new Integer[]{3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23, 101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0};
        assertEquals(54321, Day7.findMaxSignal(program));

        program = new Integer[]{3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33, 1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0};
        assertEquals(65210, Day7.findMaxSignal(program));

        int[] newSequence = new int[]{5, 6, 7, 8, 9};
        program = new Integer[]{3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26, 27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5};
        assertEquals(139629729, Day7.findMaxSignal(program, newSequence));
    }
}