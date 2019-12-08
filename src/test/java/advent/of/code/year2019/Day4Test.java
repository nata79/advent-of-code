package advent.of.code.year2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day4Test {

    @Test
    void testValidPassword() {
        assertFalse(Day4.validPassword(111111));
        assertTrue(Day4.validPassword(111122));
        assertTrue(Day4.validPassword(122345));
        assertFalse(Day4.validPassword(123444));
        assertFalse(Day4.validPassword(223450));
        assertFalse(Day4.validPassword(123789));
    }
}