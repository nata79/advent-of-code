package advent.of.code.year2019.intcode;

import advent.of.code.Utils;
import advent.of.code.year2019.intcode.operations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class IntCodeProcess {
    private final Map<Integer, Operation> operations;
    private final IOProvider ioProvider;

    private Long[] program;

    // Execution state
    private int stackPointer;
    private int relativePosition;

    public IntCodeProcess(Long[] program) {
        this(program, new StdIOProvider());
    }

    public IntCodeProcess(Long[] program, IOProvider ioProvider) {
        Long[] programCopy = new Long[program.length * 100];
        Arrays.fill(programCopy, 0L);
        System.arraycopy(program, 0, programCopy, 0, program.length);

        this.program = programCopy;
        this.ioProvider = ioProvider;

        stackPointer = 0;
        relativePosition = 0;

        operations = Map.of(
                1, new AddOperation(),
                2, new MultiplyOperation(),
                3, new InputOperation(),
                4, new OutputOperation(),
                5, new JumpIfTrueOperation(),
                6, new JumpIfFalseOperation(),
                7, new LessThanOperation(),
                8, new EqualsOperation(),
                9, new SetRelativePositionOperation(),
                99, new HaltOperation()
        );
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public void setStackPointer(int stackPointer) {
        this.stackPointer = stackPointer;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public IOProvider getIoProvider() {
        return ioProvider;
    }

    public Long[] getProgram() {
        return program;
    }

    public Long[] run() {
        while (stackPointer < program.length) {
            IntCodeInstruction instruction = readNextInstruction(program);

            if (instruction.getOperation().getOpCode() == 99)
                break;

            instruction.execute(this);
            instruction.moveStackPointer(this);
        }

        return program;
    }

    /**
     * @return true if the program finished
     */
    boolean runUntilInputNeeded(Supplier<Boolean> inputNeeded) {
        while (stackPointer < program.length) {
            IntCodeInstruction instruction = readNextInstruction(program);

            if (instruction.getOperation().getOpCode() == 3)
                if (inputNeeded.get())
                    return false;

            if (instruction.getOperation().getOpCode() == 99)
                return true;

            instruction.execute(this);
            instruction.moveStackPointer(this);
        }

        return false;
    }

    private IntCodeInstruction readNextInstruction(Long[] program) {
        List<Integer> digits = Utils.getDigits(program[stackPointer]);
        int opcode;

        // Backwards compatibility with Day 2 format
        if (digits.size() == 1) {
            opcode = digits.get(0);
        } else {
            opcode = (digits.get(digits.size() - 2) * 10) + digits.get(digits.size() - 1);
        }

        Operation operation = operations.get(opcode);
        IntCodeParameter[] parameters = new IntCodeParameter[operation.getNumberOfParameters()];

        for (int i = 0; i < operation.getNumberOfParameters(); i++) {
            int parameterModeIndex = digits.size() - 3 - i;
            int parameterMode = 0;

            if (parameterModeIndex >= 0)
                parameterMode = digits.get(parameterModeIndex);

            parameters[i] = new IntCodeParameter(
                    program,
                    relativePosition,
                    program[stackPointer + 1 + i],
                    parameterMode
            );
        }

        return new IntCodeInstruction(operation, parameters);
    }
}
