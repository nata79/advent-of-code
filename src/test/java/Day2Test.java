import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {
    @Test
    void testProcessIntcode() {
        assertArrayEquals(
                new Integer[]{2, 0, 0, 0, 99},
                Day2.processIntcode(new Integer[]{1, 0, 0, 0, 99})
        );

        assertArrayEquals(
                new Integer[]{2, 3, 0, 6, 99},
                Day2.processIntcode(new Integer[]{2, 3, 0, 3, 99})
        );

        assertArrayEquals(
                new Integer[]{2, 4, 4, 5, 99, 9801},
                Day2.processIntcode(new Integer[]{2, 4, 4, 5, 99, 0})
        );

        assertArrayEquals(
                new Integer[]{30, 1, 1, 4, 2, 5, 6, 0, 99},
                Day2.processIntcode(new Integer[]{1, 1, 1, 4, 99, 5, 6, 0, 99})
        );
    }
}