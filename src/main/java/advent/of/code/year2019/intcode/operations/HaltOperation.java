package advent.of.code.year2019.intcode.operations;

import advent.of.code.year2019.intcode.IntCodeProcess;
import advent.of.code.year2019.intcode.IntCodeParameter;

public class HaltOperation implements Operation {
    @Override
    public int getOpCode() {
        return 99;
    }

    @Override
    public int getNumberOfParameters() {
        return 0;
    }

    @Override
    public void execute(IntCodeProcess intCodeProcess, IntCodeParameter[] parameters) {
        // Nothing to do here.
    }
}
