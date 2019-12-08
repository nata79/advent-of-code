package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {
    @Test
    void testClosestIntersection() {
        assertEquals(
                159,
                Day3.closestIntersection(
                        "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(","),
                        "U62,R66,U55,R34,D71,R55,D58,R83".split(",")
                )
        );

        assertEquals(
                135,
                Day3.closestIntersection(
                        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(","),
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
                )
        );
    }

    @Test
    void testMinStepsIntersection() {
        assertEquals(
                610,
                Day3.minStepsIntersection(
                        "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(","),
                        "U62,R66,U55,R34,D71,R55,D58,R83".split(",")
                )
        );

        assertEquals(
                410,
                Day3.minStepsIntersection(
                        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(","),
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
                )
        );
    }
}