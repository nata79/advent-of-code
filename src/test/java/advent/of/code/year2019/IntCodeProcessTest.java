package advent.of.code.year2019;

import advent.of.code.Utils;
import advent.of.code.year2019.intcode.IntCodeProcess;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class IntCodeProcessTest {
    @Test
    void testProcessIntcode() {
        assertArrayEquals(
                Utils.buildBigIntegerArray(2, 0, 0, 0, 99),
                slice(new IntCodeProcess(Utils.buildBigIntegerArray(1, 0, 0, 0, 99)).run(), 5)
        );

        assertArrayEquals(
                Utils.buildBigIntegerArray(2, 3, 0, 6, 99),
                slice(new IntCodeProcess(Utils.buildBigIntegerArray(2, 3, 0, 3, 99)).run(), 5)
        );

        assertArrayEquals(
                Utils.buildBigIntegerArray(1102, 3, 0, 0, 99),
                slice(new IntCodeProcess(Utils.buildBigIntegerArray(1102, 3, 0, 3, 99)).run(), 5)
        );

        assertArrayEquals(
                Utils.buildBigIntegerArray(2, 4, 4, 5, 99, 9801),
                slice(new IntCodeProcess(Utils.buildBigIntegerArray(2, 4, 4, 5, 99, 0)).run(), 6)
        );

        assertArrayEquals(
                Utils.buildBigIntegerArray(30, 1, 1, 4, 2, 5, 6, 0, 99),
                slice(new IntCodeProcess(Utils.buildBigIntegerArray(1, 1, 1, 4, 99, 5, 6, 0, 99)).run(), 9)
        );
    }

    private BigInteger[] slice(BigInteger[] a, int length) {
        BigInteger[] result = new BigInteger[length];
        System.arraycopy(a, 0, result, 0, length);
        return result;
    }
}