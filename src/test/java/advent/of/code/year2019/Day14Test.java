package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {
    @Test
    void testFindCostOf() {
        assertEquals(
                13312,
                new Day14.Refinery(Day14.loadReactions("/day14_test_1.txt", Day14Test.class))
                        .fuelPrice()
        );

        assertEquals(
                180697,
                new Day14.Refinery(Day14.loadReactions("/day14_test_2.txt", Day14Test.class))
                        .fuelPrice()
        );

        assertEquals(
                2210736,
                new Day14.Refinery(Day14.loadReactions("/day14_test_3.txt", Day14Test.class))
                        .fuelPrice()
        );
    }
}