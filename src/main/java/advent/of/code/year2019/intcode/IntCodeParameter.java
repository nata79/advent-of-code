package advent.of.code.year2019.intcode;

import java.math.BigInteger;

public class IntCodeParameter {
    private final BigInteger[] program;
    private final int relativePosition;

    private final BigInteger integer;
    private final int mode;

    IntCodeParameter(BigInteger[] program, int relativePosition, BigInteger integer, int mode) {
        this.program = program;
        this.relativePosition = relativePosition;
        this.integer = integer;
        this.mode = mode;
    }

    public int getPointer() {
        switch (mode) {
            case 0:
            case 1:
                return integer.intValue();
            case 2:
                return relativePosition + integer.intValue();
            default:
                throw new IllegalArgumentException("Invalid mode");
        }
    }

    public BigInteger getValue() {
        if (mode == 1)
            return integer;
        else
            return program[getPointer()];
    }
}
