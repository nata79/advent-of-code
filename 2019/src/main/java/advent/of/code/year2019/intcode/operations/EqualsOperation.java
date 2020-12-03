package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeParameter;
import advent.of.code.year2019.intcode.IntCodeProcess;

public class EqualsOperation implements Operation {
    @Override
    public int getOpCode() {
        return 8;
    }

    @Override
    public int getNumberOfParameters() {
        return 3;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        Long[] program = intCodeProcess.getProgram();
        program[parameters[2].getPointer()] = parameters[0].getValue().equals(parameters[1].getValue()) ?
                1L :
                0L;
    }
}
