package advent.of.code.year2019;

import advent.of.code.Utils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

class IntCode {
    private final Map<Integer, Operation> operations;

    IntCode() {
        this(new StdIOProvider());
    }

    IntCode(IOProvider ioProvider) {
        operations = Map.of(
                1,
                new ProcedureOperation(
                        1,
                        2,
                        0,
                        true,
                        (parameters, returnPosition, program) -> program[returnPosition] = parameters[0] + parameters[1]
                ),
                2,
                new ProcedureOperation(
                        2,
                        2,
                        0,
                        true,
                        (parameters, returnPosition, program) -> program[returnPosition] = parameters[0] * parameters[1]
                ),
                3,
                new ProcedureOperation(
                        3,
                        0,
                        0,
                        true,
                        (parameters, returnPosition, program) -> program[returnPosition] = ioProvider.nextInt()
                ),
        4,
                new ProcedureOperation(
                        4,
                        1,
                        0,
                        false,
                        (parameters, returnPosition, program) -> ioProvider.putInt(parameters[0])
                ),
                5,
                new GoToOperation(
                        5,
                        2,
                        0,
                        (parameters) -> parameters[0] != 0
                ),
                6,
                new GoToOperation(
                        6,
                        2,
                        0,
                        (parameters) -> parameters[0] == 0
                ),
                7,
                new ProcedureOperation(
                        7,
                        2,
                        0,
                        true,
                        (parameters, returnPosition, program) -> program[returnPosition] = parameters[0] < parameters[1] ? 1 : 0
                ),
                8,
                new ProcedureOperation(
                        8,
                        2,
                        0,
                        true,
                        (parameters, returnPosition, program) -> program[returnPosition] = parameters[0] == parameters[1] ? 1 : 0
                ),
                99,
                new ProcedureOperation(
                        99,
                        0,
                        0,
                        false,
                        null
                )
        );
    }

    Integer[] processIntcode(Integer[] program) {
        Integer[] programCopy = new Integer[program.length];
        System.arraycopy(program, 0, programCopy, 0, program.length);

        int stackPointer = 0;

        while (stackPointer < programCopy.length) {
            Instruction instruction = readNextInstruction(programCopy, stackPointer);

            if (instruction.getOperation().getOpcode() == 99)
                break;

            stackPointer = instruction.execute(programCopy, stackPointer);
        }

        return programCopy;
    }

    private Instruction readNextInstruction(Integer[] program, int stackPointer) {
        List<Integer> digits = Utils.getDigits(program[stackPointer]);
        int opcode;

        // Backwards compatibility with Day 2 format
        if (digits.size() == 1) {
            opcode = digits.get(0);
        } else {
            opcode = (digits.get(digits.size() - 2) * 10) + digits.get(digits.size() - 1);
        }

        Operation operation = operations.get(opcode);
        int[] parameters = new int[operation.getNumberOfParameters()];

        for (int i = 0; i < operation.getNumberOfParameters(); i++) {
            int parameterModeIndex = digits.size() - 3 - i;
            int parameterMode = operation.getDefaultParameterMode();

            if (parameterModeIndex >= 0)
                parameterMode = digits.get(parameterModeIndex);

            if (parameterMode == 0)
                parameters[i] = program[program[stackPointer + 1 + i]];
            else
                parameters[i] = program[stackPointer + 1 + i];
        }

        int returnPosition = operation.hasReturnValue()
                ? program[stackPointer + 1 + operation.getNumberOfParameters()]
                : -1;

        return new Instruction(operation, parameters, returnPosition);
    }

    interface IOProvider {
        int nextInt();

        void putInt(int value);
    }

    private static class StdIOProvider implements IOProvider {
        @Override
        public int nextInt() {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        }

        @Override
        public void putInt(int value) {
            System.out.println(value);
        }
    }

    private static class Instruction {
        private final Operation operation;
        private final int[] parameters;
        private final int returnPosition;

        Instruction(Operation operation, int[] parameters, int returnPosition) {
            this.operation = operation;
            this.parameters = parameters;
            this.returnPosition = returnPosition;
        }

        Operation getOperation() {
            return operation;
        }

        int execute(Integer[] program, int stackPointer) {
            return operation.execute(parameters, returnPosition, program, stackPointer);
        }
    }

    private static abstract class Operation {
        private final int opcode;
        private final int numberOfParameters;
        private final int defaultParameterMode;

        Operation(int opcode, int numberOfParameters, int defaultParameterMode) {
            this.opcode = opcode;
            this.numberOfParameters = numberOfParameters;
            this.defaultParameterMode = defaultParameterMode;
        }

        int getOpcode() {
            return opcode;
        }

        int getNumberOfParameters() {
            return numberOfParameters;
        }

        int getDefaultParameterMode() {
            return defaultParameterMode;
        }

        boolean hasReturnValue() {
            return false;
        }

        abstract int execute(int[] parameters, int returnPosition, Integer[] program, int stackPointer);
    }

    @FunctionalInterface
    private interface ProcedureOperationExecutor {
        void apply(int[] parameters, int returnPosition, Integer[] program);
    }

    private static class ProcedureOperation extends Operation {
        private final boolean hasReturnValue;
        private final ProcedureOperationExecutor action;

        ProcedureOperation(int opcode, int numberOfParameters, int defaultParameterMode, boolean hasReturnValue,
                           ProcedureOperationExecutor action) {
            super(opcode, numberOfParameters, defaultParameterMode);
            this.hasReturnValue = hasReturnValue;
            this.action = action;
        }

        @Override
        boolean hasReturnValue() {
            return hasReturnValue;
        }

        @Override
        int execute(int[] parameters, int returnPosition, Integer[] program, int stackPointer) {
            this.action.apply(parameters, returnPosition, program);

            int returnPositionSize = hasReturnValue ? 1 : 0;
            int size = getNumberOfParameters() + 1 + returnPositionSize;
            return stackPointer + size;
        }
    }

    private static class GoToOperation extends Operation {
        private final Function<int[], Boolean> condition;

        GoToOperation(int opcode, int numberOfParameters, int defaultParameterMode, Function<int[], Boolean> condition) {
            super(opcode, numberOfParameters, defaultParameterMode);
            this.condition = condition;
        }

        @Override
        int execute(int[] parameters, int returnPosition, Integer[] program, int stackPointer) {
            if (condition.apply(parameters))
                return parameters[1];
            else
                return stackPointer + 1 + getNumberOfParameters();
        }
    }
}
