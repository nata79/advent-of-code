package advent.of.code.year2019.intcode;

public class IntCodeParameter {
    private final Long[] program;
    private final int relativePosition;

    private final Long integer;
    private final int mode;

    IntCodeParameter(Long[] program, int relativePosition, Long integer, int mode) {
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

    public Long getValue() {
        if (mode == 1)
            return integer;
        else
            return program[getPointer()];
    }
}
