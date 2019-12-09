package advent.of.code.year2019.intcode;

import advent.of.code.year2019.intcode.operations.Operation;

class IntCodeInstruction {
    private final Operation operation;
    private final IntCodeParameter[] parameters;

    IntCodeInstruction(Operation operation, IntCodeParameter[] parameters) {
        this.operation = operation;
        this.parameters = parameters;
    }

    Operation getOperation() {
        return operation;
    }

    void execute(IntCodeProcess intCodeProcess) {
        operation.execute(intCodeProcess, parameters);
    }

    void moveStackPointer(IntCodeProcess intCodeProcess) {
        operation.moveStackPointer(intCodeProcess, parameters);
    }
}
