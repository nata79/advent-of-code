package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeParameter;
import advent.of.code.year2019.intcode.IntCodeProcess;

public class JumpIfFalseOperation implements Operation {
    @Override
    public int getOpCode() {
        return 6;
    }

    @Override
    public int getNumberOfParameters() {
        return 2;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        // Nothing to do here.
    }

    @Override
    public void moveStackPointer(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        if (parameters[0].getValue().equals(0L))
            intCodeProcess.setStackPointer(parameters[1].getValue().intValue());
        else
            intCodeProcess.setStackPointer(intCodeProcess.getStackPointer() + 1 + getNumberOfParameters());
    }
}
