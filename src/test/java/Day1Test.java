import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    @org.junit.jupiter.api.Test
    void testCalculateFuel() {
        assertEquals(2, Day1.calculateFuel(12));
        assertEquals(2, Day1.calculateFuel(14));
        assertEquals(654, Day1.calculateFuel(1969));
        assertEquals(33583, Day1.calculateFuel(100756));
    }

    @Test
    void testCalculateFuelWithFuel() {
        assertEquals(2, Day1.calculateFuelWithFuel(12));
        assertEquals(966, Day1.calculateFuelWithFuel(1969));
        assertEquals(50346, Day1.calculateFuelWithFuel(100756));
    }
}