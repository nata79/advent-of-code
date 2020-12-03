package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeParameter;

public class OutputOperation implements Operation {
    @Override
    public int getOpCode() {
        return 4;
    }

    @Override
    public int getNumberOfParameters() {
        return 1;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        intCodeProcess.getIoProvider().putInt(parameters[0].getValue());
    }
}
