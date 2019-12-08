package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntCodeTest {
    @Test
    void testProcessIntcode() {
        IntCode intCode = new IntCode();

        assertArrayEquals(
                new Integer[]{2, 0, 0, 0, 99},
                intCode.processIntcode(new Integer[]{1, 0, 0, 0, 99})
        );

        assertArrayEquals(
                new Integer[]{2, 3, 0, 6, 99},
                intCode.processIntcode(new Integer[]{2, 3, 0, 3, 99})
        );

        assertArrayEquals(
                new Integer[]{1102, 3, 0, 0, 99},
                intCode.processIntcode(new Integer[]{1102, 3, 0, 3, 99})
        );

        assertArrayEquals(
                new Integer[]{2, 4, 4, 5, 99, 9801},
                intCode.processIntcode(new Integer[]{2, 4, 4, 5, 99, 0})
        );

        assertArrayEquals(
                new Integer[]{30, 1, 1, 4, 2, 5, 6, 0, 99},
                intCode.processIntcode(new Integer[]{1, 1, 1, 4, 99, 5, 6, 0, 99})
        );
    }
}