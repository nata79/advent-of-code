package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    private List<Day12.Moon> getMoons() {
        return List.of(
                new Day12.Moon(new int[]{-1, 0, 2}),
                new Day12.Moon(new int[]{2, -10, -7}),
                new Day12.Moon(new int[]{4, -8, 8}),
                new Day12.Moon(new int[]{3, 5, -1})
        );
    }

    @Test
    void testStep() {
        List<Day12.Moon> moons = getMoons();
        Day12.step(moons);

        // pos=<x= 2, y=-1, z= 1>, vel=<x= 3, y=-1, z=-1>
        Day12.Moon moon = moons.get(0);

        int[] position = moon.getPosition();
        assertArrayEquals(new int[]{2, -1, 1}, position);

        int[] velocity = moon.getVelocity();
        assertArrayEquals(new int[]{3, -1, -1}, velocity);
    }

    @Test
    void testEnergyAfterSteps() {
        assertEquals(179, Day12.energyAfterSteps(getMoons(), 10));
    }

    @Test
    void testStepsToRepeatUniverse() {
        assertEquals(2772, Day12.stepsToRepeatUniverse(getMoons()));
    }
}